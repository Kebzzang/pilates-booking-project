import React, { useState } from 'react';
import { FaUserEdit } from 'react-icons/all';
import { Link, useHistory, useParams } from 'react-router-dom';

import { IClass } from '../../types/db';
import fetcher from '../../utils/fetcher';
import { Tab, Table, Tabs } from 'react-bootstrap';

import './teachercard.css';
import useSWR from 'swr';
import Loading from '../../layouts/Loading';
import { CourseListItem } from './CourseListItem';
import defaultProfile from '../../img/defaultProfile.png';
import { ImgPreview, ImgContainer, RegisterTeacherContainer, DetailDiv } from './style/SaveStyle';
import { DetailTitle } from './style/DetailStyle';
import { NoClass } from '../Calendar/style';
const TeacherCardDetail = () => {
  const history = useHistory();
  const { teacherId } = useParams<{ teacherId: string }>();
  const { data: teacher, error } = useSWR(`http://localhost:8000/api/v1/teacher/${teacherId}`, fetcher);
  const [key, setKey] = useState<string | null>('Profile');
  if (!teacher) return <Loading />;

  const handleImgError = (e: any) => {
    e.target.src = defaultProfile;
  };
  const updateTeacher = () => {
    history.push(`/teachers/${teacherId}/update`);
  };
  return (
    <RegisterTeacherContainer>
      <DetailDiv>
        <ImgContainer>
          <ImgPreview
            src={`http://d1djtzszdq7pt7.cloudfront.net/${teacher.id}/${teacher.userProfileImageLink}`}
            //src={`http://localhost:8000/api/v1/teacher/${teacherData.id}/download`}
            onError={handleImgError}
          />
        </ImgContainer>
        <button className="update-icon" onClick={updateTeacher}>
          <FaUserEdit size="24" />
        </button>
        <Tabs id="controlled-tab-example" activeKey={key} onSelect={(eventKey) => setKey(eventKey)}>
          <Tab eventKey="Profile" title="Profile">
            <DetailTitle>
              <h2>{teacher.name}</h2>
              <h5>{teacher.email}</h5>
            </DetailTitle>
            <p style={{ wordBreak: 'break-all' }}>{teacher.about}</p>
          </Tab>
          <Tab eventKey="Lessons" title="Lessons">
            {teacher.courses.length !== 0 ? (
              <Table style={{ textAlign: 'center', marginTop: '10px' }} hover>
                <thead>
                  <tr>
                    <th>Title</th>
                    <th>Type</th>
                    <th>Instructor</th>
                    <th>Quota</th>
                  </tr>
                </thead>
                <tbody>
                  {teacher.courses.map((element: IClass) => (
                    <CourseListItem key={element.id} courseData={element} />
                  ))}
                </tbody>
              </Table>
            ) : (
              <NoClass>No Class</NoClass>
            )}
          </Tab>
        </Tabs>
      </DetailDiv>
    </RegisterTeacherContainer>
  );
};

export default TeacherCardDetail;
