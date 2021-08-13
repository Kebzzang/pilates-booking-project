import React, { useState, useEffect } from 'react';
import TeacherCard from './TeacherCard';
import axios from 'axios';
import t1 from '../../img/t1.jpg';
import t2 from '../../img/t2.jpg';
import t3 from '../../img/t3.jpg';
import { ITeacher } from '../../types/db';
const Teachers = () => {
  const [profiles, setProfiles] = useState<ITeacher[]>([]);

  useEffect(() => {
    axios
      .get('http://localhost:8000/api/v1/user/teacher', {
        withCredentials: true,
      })
      .then((r) => {
        if (r.status !== 204) {
          setProfiles(r.data.data);
        }
      });
  }, []);

  return (
    <div className="row Card-Container">
      {profiles.map((profile) => (
        <TeacherCard key={profile.id} teacherData={profile} />
      ))}
    </div>
  );
};

export default Teachers;
