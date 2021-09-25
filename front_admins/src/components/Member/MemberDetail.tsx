import React, { FC, useCallback, useEffect, useState } from 'react';
import { useParams, useLocation, useHistory } from 'react-router-dom';
import { Button, DetailDiv, Form, Input, Label, RegisterTeacherContainer, Select } from '../Teachers/style/SaveStyle';
import { IClass, IUser } from '../../types/db';
import { DetailTitle } from '../Teachers/style/DetailStyle';
import { RoleBadge } from './style';
import useInput from '../../hooks/useInput';
import { Tab, Tabs } from 'react-bootstrap';
import axios from 'axios';

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
    <div>
      {data.map((element) => (
        <div>{element.title}</div>
      ))}
    </div>
  );
};

export default MemberDetail;
