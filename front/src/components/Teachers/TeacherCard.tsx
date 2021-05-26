import React, { FC, useState } from 'react';
import './teachercard.css';
interface PropsType {
  name: string;
  email: string;
  image: any;
}
const TeacherCard: FC<PropsType> = ({ name, image, email }) => {
  const about =
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ';

  return (
    <div className="Card">
      <div className="upper-container">
        <div className="image-container">
          <img src={image} alt="" height="100px" width="100px" />
        </div>
      </div>
      <div className="lower-container">
        <h4>{name}</h4>
        <p>{about}</p>
        <h5>{email}</h5>
      </div>
    </div>
  );
};

export default TeacherCard;
