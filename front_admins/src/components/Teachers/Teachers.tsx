import React, { useState, useEffect } from 'react';
import TeacherCard from './TeacherCard';
import axios from 'axios';
import { IClass, ITeacher } from '../../types/db';
import useInput from '../../hooks/useInput';
import InputGroup from 'react-bootstrap/InputGroup';
import { Dropdown, DropdownButton, FormControl } from 'react-bootstrap';
import useTeacherFetch from '../../hooks/useTeacherFetch';

const Teachers = () => {
  const [myCategory, setMyCategory] = useState('employed');
  const profiles = useTeacherFetch();
  const [filteredProfiles, setFilteredProfiles] = useState<ITeacher[]>([]);
  const [mySearch, onChangeMySearch] = useInput('');

  useEffect(() => {
    myCategory === 'all'
      ? setFilteredProfiles(profiles)
      : myCategory === 'employed'
      ? setFilteredProfiles(profiles.filter((teacher) => teacher.working === true))
      : setFilteredProfiles(profiles.filter((teacher) => teacher.working === false));
  }, [myCategory, profiles]);

  function searchTeacher(ITeachers: ITeacher[]) {
    let lowerMySearch = mySearch.toLowerCase();
    return ITeachers.filter(
      (ITeacher) =>
        ITeacher.name.toLowerCase().indexOf(lowerMySearch) > -1 ||
        ITeacher.email.toLowerCase().indexOf(lowerMySearch) > -1 ||
        ITeacher.about.toLowerCase().indexOf(lowerMySearch) > -1,
    );
  }
  function selectWorking(value: string) {
    setMyCategory(value);
    console.log(myCategory);
  }

  return (
    <div>
      <InputGroup
        className="mb-3"
        style={{ width: '400px', marginLeft: 'auto', marginTop: '20px', marginRight: 'auto' }}
      >
        <FormControl onChange={onChangeMySearch} aria-label="Text input with dropdown button" placeholder="Search" />
        <DropdownButton variant="outline-secondary" title={myCategory} id="input-group-dropdown-2">
          <Dropdown.Item id="all" onClick={(e) => selectWorking(e.currentTarget.id)}>
            all
          </Dropdown.Item>
          <Dropdown.Item id="employed" onClick={(e) => selectWorking(e.currentTarget.id)}>
            Employed
          </Dropdown.Item>
          <Dropdown.Item id="resigned" onClick={(e) => selectWorking(e.currentTarget.id)}>
            Resigned
          </Dropdown.Item>
        </DropdownButton>
      </InputGroup>
      <div className="row Card-Container">
        {searchTeacher(filteredProfiles).map((profile) => (
          <TeacherCard key={profile.id} teacherData={profile} />
        ))}
      </div>
    </div>
  );
};

export default Teachers;
