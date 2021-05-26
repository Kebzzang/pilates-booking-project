import React, { useState } from 'react';
import TeacherCard from './TeacherCard';
import t1 from '../../img/t1.jpg';
import t2 from '../../img/t2.jpg';
import t3 from '../../img/t3.jpg';
const Teachers = () => {
  const [profile, setProfile] = useState([
    { name: 'Sammie Que', image: t1, email: 'SammieQue@email.com' },
    { name: 'Ellie Kang', image: t2, email: 'EllieKang@email.com' },
    { name: 'Yun Kim', image: t3, email: 'YunKim@email.com' },
  ]);
  return (
    <div className="row Card-Container">
      {profile.map((profile) => (
        <TeacherCard key={profile.name} name={profile.name} image={profile.image} email={profile.email} />
      ))}
    </div>
  );
};

export default Teachers;
