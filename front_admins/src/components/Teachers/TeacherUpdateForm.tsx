import React, { useCallback, useState } from 'react';
import {
  Button,
  Error,
  Form,
  ImgContainer,
  ImgPreview,
  Input,
  Label,
  RegisterTeacherContainer,
  Select,
  TextArea,
} from './style/SaveStyle';
import Loading from '../../layouts/Loading';
import useInput from '../../hooks/useInput';
import defaultProfile from '../../img/defaultProfile.png';
import { Redirect, useHistory, useParams } from 'react-router-dom';
import axios from 'axios';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';

const TeacherUpdateForm = () => {
  //{`http://d1djtzszdq7pt7.cloudfront.net/${teacher.name}/${teacher.userProfileImageLink}`}
  const { teacherId } = useParams<{ teacherId: string }>();
  const { data: teacher, error, mutate } = useSWR(`http://localhost:8000/api/v1/teacher/${teacherId}`, fetcher);

  const [name, onChangeName] = useInput(teacher.name);
  const [email, onChangeEmail] = useInput(teacher.email);
  const [about, onChangeAbout] = useInput(teacher.about);
  const [working, onChangeWorking] = useInput(teacher.working);

  const [userProfile, setUserProfile] = useState<any>(
    `http://d1djtzszdq7pt7.cloudfront.net/${teacherId}/${teacher.userProfileImageLink}`,
  ); //사진 미리보기 용
  const [file, setFile] = useState<File>(); //실제 api 에 보낼 사진 파일
  const history = useHistory();
  const onUpdateSubmit = useCallback(
    async (e) => {
      e.preventDefault();
      const teacherInfo = {
        name: name,
        email: email,
        working: working,
        about: about,
      };
      const formData = new FormData();
      if (file) {
        console.log(file.toString());
        formData.append('file', file);
      }
      formData.append('key', new Blob([JSON.stringify(teacherInfo)], { type: 'application/json' }));

      await axios.put(`http://localhost:8000/api/v1/admin/teacher/${teacherId}`, formData, { withCredentials: true });
      await mutate();
      history.push(`/teachers/${teacherId}`);
    },
    [about, email, file, history, mutate, name, teacherId, working],
  );
  const imageHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const reader = new FileReader();
    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];
      reader.onloadend = () => {
        if (reader.result !== null) setUserProfile(reader.result);
        setFile(file);
      };
      reader.readAsDataURL(file);
    }
  };
  const handleImgError = (e: any) => {
    e.target.src = defaultProfile;
  };
  if (!teacher || error) {
    return <Loading />;
  }

  return (
    <RegisterTeacherContainer>
      <Form onSubmit={onUpdateSubmit}>
        <Label>
          <ImgContainer>
            <ImgPreview onError={handleImgError} src={userProfile} />
          </ImgContainer>
          <span>Profile Image</span>
          <input type="file" accept="image/*" name="userProfileImageLink" onChange={imageHandler} />
        </Label>
        <Label id="working-label">
          <span>State {working.toString()}</span>
          <div>
            <Select key={working.toString()} defaultValue={working.toString()} onChange={onChangeWorking}>
              <option value="true">Working</option>
              <option value="false">Resigned</option>
            </Select>
          </div>
        </Label>
        <Label id="name-label">
          <span>Name </span>
          <div>
            <Input value={name} type="name" onChange={onChangeName} required={true} />
          </div>
        </Label>
        {!name && <Error>Input Name.</Error>}
        <Label id="email-label">
          <span>Email</span>
          <div>
            <Input type="email" id="email" name="email" value={email} onChange={onChangeEmail} required={true} />
          </div>
        </Label>
        {!email && <Error>Input Email.</Error>}
        <Label id="about-label">
          <span>About</span>
          <div>
            <TextArea id="about" name="about" value={about} onChange={onChangeAbout} required={true} />
          </div>
        </Label>
        {!about && <Error>Input About</Error>}
        <Button type="submit">Update</Button>
      </Form>
    </RegisterTeacherContainer>
  );
};

export default TeacherUpdateForm;
