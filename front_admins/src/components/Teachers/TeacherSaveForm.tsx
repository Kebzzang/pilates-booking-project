import React, { useCallback, useState } from 'react';
import {
  Button,
  Error,
  Form,
  ImgPreview,
  Input,
  Label,
  RegisterTeacherContainer,
  TextArea,
  ImgContainer,
} from './style/SaveStyle';
import axios from 'axios';
import useInput from '../../hooks/useInput';
import defaultProfile from '../../img/defaultProfile.png';
import './teachercard.css';
import { useHistory } from 'react-router-dom';
import Loading from '../../layouts/Loading';
const TeacherSaveForm = () => {
  const [name, onChangeTeacherName] = useInput('');
  const [email, onChangeEmail] = useInput('');
  const [about, onChangeAbout] = useInput('');
  const [teacherId, setTeacherId] = useState('');
  const [saveSuccess, setSaveSuccess] = useState(false);
  const [saveError, setSaveError] = useState('');
  const [loading, setLoading] = useState(false);
  const [userProfile, setUserProfile] = useState<any>(defaultProfile); //사진 미리보기 용
  const [file, setFile] = useState<File>(); //실제 api 에 보낼 사진 파일
  const history = useHistory();

  const onsubmit = useCallback(
    (e) => {
      e.preventDefault();
      setSaveSuccess(false);
      setLoading(true);
      if (file !== undefined) {
        //사진 파일도 같이 변경하고 싶다면
        const formData = new FormData();
        const teacherInfo = {
          name: name,
          email: email,
          about: about,
        };
        formData.append('file', file);
        formData.append('key', new Blob([JSON.stringify(teacherInfo)], { type: 'application/json' }));

        axios
          .post('http://localhost:8000/api/v1/admin/teacher', formData, {
            withCredentials: true,
            headers: {
              'Content-Type': `multipart/form-data`,
            },
          })
          .then((response) => {
            setSaveSuccess(true);
            history.push(
              '/teachers/' + response.headers.location.slice(response.headers.location.lastIndexOf('/') + 1),
            );
            setLoading(false);
            alert('Save Success! See details');
          })
          .catch((error) => {
            setSaveError(error.response);
            setLoading(false);
          });
      } else {
        //일반적으로 사진은 그대로이고 이름, 이메일, 강사설명만 바꾸고 싶다면!!
      }
    },
    [file, name, email, about, history],
  );
  const handleImgError = (e: any) => {
    e.target.src = defaultProfile;
  };
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

  return (
    <RegisterTeacherContainer>
      <Form onSubmit={onsubmit}>
        <Label>
          <ImgContainer>
            <ImgPreview src={userProfile} onError={handleImgError} />
          </ImgContainer>
          <span>Profile Image (Recommends 1:1 ratio image)</span>
          <input type="file" accept="image/*" name="userprofileImage" onChange={imageHandler} required={true} />
        </Label>
        <Label id="name-label">
          <span>Name </span>
          <div>
            <Input value={name} type="name" onChange={onChangeTeacherName} required={true} />
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
        {!email && <Error>Input About</Error>}
        <Button type="submit">Register</Button>
      </Form>
      {loading ? <Loading /> : ''}
    </RegisterTeacherContainer>
  );
};

export default TeacherSaveForm;
