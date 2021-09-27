import React, { FC, useCallback, useEffect, useState } from 'react';
import { useParams, useLocation, Link } from 'react-router-dom';
import { Button, DetailDiv, RegisterTeacherContainer, Select } from '../Teachers/style/SaveStyle';
import { IClass, IUser } from '../../types/db';
import { DetailTitle } from '../Teachers/style/DetailStyle';

import axios from 'axios';
import { CourseListItem } from '../Teachers/CourseListItem';
import { RoleBadge } from './style';
import { Table } from 'react-bootstrap';

const MemberDetail = () => {
  const { memberId } = useParams<{ memberId: string }>();
  const { state: userData } = useLocation<IUser>();
  const [data, setData] = useState<IClass[]>([]);
  useEffect(() => {
    if (userData.role === 'ROLE_USER') {
      axios
        .get(`http://localhost:8000/api/v1/course/me/${memberId}`, { withCredentials: true })
        .then((res) => setData(res.data.data));
    }
  }, [memberId, userData.role]);

  return (
    <RegisterTeacherContainer>
      <DetailDiv>
        <DetailTitle>
          <h2>{userData.username}</h2>{' '}
          <h5>
            {userData.email} / <RoleBadge roleStyle={userData.role}>{userData.role.slice(5)}</RoleBadge>
          </h5>
        </DetailTitle>{' '}
        <Table style={{ textAlign: 'center' }} borderless>
          <thead>
            <tr>
              <th>Title</th>
              <th>Type</th>
              <th style={{ textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>Instructor</th>
              <th>Quota</th>
            </tr>
          </thead>
          <tbody>
            {data.map((element) => (
              <CourseListItem key={element.id} courseData={element} />
            ))}
          </tbody>
        </Table>
      </DetailDiv>
    </RegisterTeacherContainer>
  );
};

export default MemberDetail;
