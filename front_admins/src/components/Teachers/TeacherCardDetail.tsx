import React, { EventHandler, SetStateAction, useEffect, useState } from 'react';
import { AiOutlineUserAdd } from 'react-icons/all';
import { Link, Route, useParams, useRouteMatch } from 'react-router-dom';
import TeacherUpdate from './TeacherUpdate';
import { ITeacher } from '../../types/db';
import fetcher from '../../utils/fetcher';
import { Tab, Tabs } from 'react-bootstrap';
import { SelectComponents } from 'react-select/src/components';
import { SelectCallback } from 'react-bootstrap/helpers';
import { RegisterTeacherContainer } from './style/SaveStyle';

const TeacherCardDetail = () => {
  const { path } = useRouteMatch();
  const { teacherId } = useParams<{ teacherId: string }>();
  const [teacher, setTeacher] = useState<ITeacher>();
  const [key, setKey] = useState<string | null>('Profile');
  useEffect(() => {
    fetcher(`http://localhost:8000/api/v1/teacher/${teacherId}`).then((r) => console.log(r));
  }, []);

  return (
    <RegisterTeacherContainer>
      <Link className="update-icon" to={`/teachers/${teacherId}/update`}>
        <AiOutlineUserAdd size="24" />
      </Link>
      <Tabs id="controlled-tab-example" activeKey={key} onSelect={(eventKey) => setKey(eventKey)} className="mb-3">
        <Tab eventKey="Profile" title="Profile">
          profile tab
        </Tab>
        <Tab eventKey="Lessons" title="Lessons">
          lessons tab
        </Tab>
      </Tabs>
    </RegisterTeacherContainer>
  );
};

export default TeacherCardDetail;
