import React, { useState, useEffect } from 'react';
import TeacherCard from './TeacherCard';
import axios from 'axios';
import t1 from '../../img/t1.jpg';
import t2 from '../../img/t2.jpg';
import t3 from '../../img/t3.jpg';
import { ITeacher } from '../../types/db';
import useInput from '../../hooks/useInput';
import { FormControl } from 'react-bootstrap';
import InputGroup from 'react-bootstrap/InputGroup';
const Teachers = () => {
  const [profiles, setProfiles] = useState<ITeacher[]>([]);
  const [mySearch, onChangeMySearch] = useInput('');
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
  function searchTeacher(ITeachers: ITeacher[]) {
    let lowerMySearch = mySearch.toLowerCase();
    return ITeachers.filter(
      (
        ITeacher, //찾게 되면 0 해당 위치의 인덱스를 리턴한다
      ) =>
        ITeacher.name.toLowerCase().indexOf(lowerMySearch) > -1 ||
        ITeacher.email.toLowerCase().indexOf(lowerMySearch) > -1 ||
        ITeacher.about.toLowerCase().indexOf(lowerMySearch) > -1,
    );
  }
  return (
    <div>
      <InputGroup
        className="mb-3"
        style={{ width: '400px', marginLeft: 'auto', marginTop: '20px', marginRight: 'auto' }}
      >
        {' '}
        <FormControl onChange={onChangeMySearch} aria-label="Text input with dropdown button" placeholder="Search" />
      </InputGroup>
      <div className="row Card-Container">
        {searchTeacher(profiles).map((profile) => (
          <TeacherCard key={profile.id} teacherData={profile} />
        ))}
      </div>
    </div>
  );
};

export default Teachers;
