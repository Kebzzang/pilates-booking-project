import React, { FC } from 'react';
import './teachercard.css';
import { ITeacher } from '../../types/db';
import defaultProfile from '../../img/defaultProfile.png';
import { GiHamburgerMenu } from 'react-icons/all';
import { Link } from 'react-router-dom';
import { GradientDiv } from './style/TeacherCard';

interface PropsType {
  teacherData: ITeacher;
}
const TeacherCard: FC<PropsType> = ({ teacherData }) => {
  const handleImgError = (e: any) => {
    e.target.src = defaultProfile;
  };
  return (
    <div className="Card">
      <GradientDiv degree={teacherData.id}>
        <div className="image-container">
          <img
            src={`http://d1djtzszdq7pt7.cloudfront.net/${teacherData.id}/${teacherData.userProfileImageLink}`}
            onError={handleImgError}
          />
        </div>
      </GradientDiv>
      <div className="lower-container">
        <h4>{teacherData.name}</h4>
        <p>{teacherData.about}</p>
        <h5>{teacherData.email}</h5>
        <Link className="update-icon" to={`/teachers/${teacherData.id}`}>
          <GiHamburgerMenu color="black" size="24" style={{ marginRight: '10px' }} />
        </Link>
      </div>
    </div>
  );
};

export default TeacherCard;
