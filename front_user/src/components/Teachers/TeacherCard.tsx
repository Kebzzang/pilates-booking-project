import React, { FC, useState } from 'react';
import './teachercard.css';
import { ITeacher } from '../../types/db';
import defaultProfile from '../../img/defaultProfile.png';
interface PropsType {
  teacherData: ITeacher;
}
const TeacherCard: FC<PropsType> = ({ teacherData }) => {
  const handleImgError = (e: any) => {
    e.target.src = defaultProfile;
  };
  return (
    <div className="Card">
      <div className="upper-container">
        <div className="image-container">
          <img
            src={`http://d1djtzszdq7pt7.cloudfront.net/${teacherData.id}/${teacherData.userProfileImageLink}`}
            //src={`http://localhost:8000/api/v1/teacher/${teacherData.id}/download`}
            alt=""
            height="100px"
            width="100px"
            onError={handleImgError}
          />
        </div>
      </div>
      <div className="lower-container">
        <h4>{teacherData.name}</h4>
        <p>{teacherData.about}</p>
        <h5>{teacherData.email}</h5>
      </div>
    </div>
  );
};

export default TeacherCard;
