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
  const about =
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ';
  return (
    <div className="Card">
      <div className="upper-container">
        <div className="image-container">
          <img
            src={`http://localhost:8000/api/v1/teacher/${teacherData.id}/download`}
            alt=""
            height="100px"
            width="100px"
            onError={handleImgError}
          />
        </div>
      </div>
      <div className="lower-container">
        <h4>{teacherData.name}</h4>
        <p>{about}</p>
        <h5>{teacherData.email}</h5>
      </div>
    </div>
  );
};

export default TeacherCard;
