import React, { useState } from 'react';
import { FaUserEdit } from 'react-icons/all';
import { Link, useHistory, useParams, useRouteMatch } from 'react-router-dom';

import { IClass } from '../../types/db';
import fetcher from '../../utils/fetcher';
import { Tab, Tabs } from 'react-bootstrap';

import './teachercard.css';
import useSWR from 'swr';
import Loading from '../../layouts/Loading';
import { CourseListItem } from './CourseListItem';
import defaultProfile from '../../img/defaultProfile.png';
import { ImgPreview, ImgContainer, RegisterTeacherContainer, DetailDiv } from './style/SaveStyle';
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
            <h4>{teacher.name}</h4>
            <h5>
              {teacher.userProfileImageLink} {teacher.working.toString()}
            </h5>
            <h5>{teacher.email}</h5>
            <h6>{teacher.about}</h6>
          </Tab>
          <Tab eventKey="Lessons" title="Lessons">
            {teacher.courses &&
              teacher.courses.map((element: IClass) => <CourseListItem key={element.id} courseData={element} />)}
          </Tab>
        </Tabs>
      </DetailDiv>
    </RegisterTeacherContainer>
  );
};

export default TeacherCardDetail;
