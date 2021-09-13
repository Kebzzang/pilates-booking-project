import React, { useCallback, useEffect, useState } from 'react';
import {
  Button,
  Error,
  Form,
  ImgContainer,
  ImgPreview,
  Input,
  Label,
  RegisterTeacherContainer,
  TextArea,
} from './style/SaveStyle';
import Loading from '../../layouts/Loading';
import useInput from '../../hooks/useInput';
import defaultProfile from '../../img/defaultProfile.png';
import { useHistory, useParams, useRouteMatch } from 'react-router-dom';
import axios from 'axios';

const TeacherUpdate = () => {
  //{`http://d1djtzszdq7pt7.cloudfront.net/${teacher.name}/${teacher.userProfileImageLink}`}
  const { teacherId } = useParams<{ teacherId: string }>();

  const [name, onChangeTeacherName, setName] = useInput('');
  const [email, onChangeEmail, setEmail] = useInput('');
  const [about, onChangeAbout, setAbout] = useInput('');

  const [saveSuccess, setSaveSuccess] = useState(false);
  const [saveError, setSaveError] = useState('');
  const [loading, setLoading] = useState(false);
  const [userProfile, setUserProfile] = useState<any>(defaultProfile); //사진 미리보기 용
  const [file, setFile] = useState<File>(); //실제 api 에 보낼 사진 파일
  const history = useHistory();
  useEffect(() => {
    axios.get(`http://localhost:8000/api/v1/teacher/${teacherId}`, { withCredentials: true }).then((r) => {
      // setName(r.data.data.name);
      // setAbout(r.data.data.about);
      // setEmail(r.data.data.email);
      setName(r.data.data.name);
      setEmail(r.data.data.email);
      setAbout(r.data.data.about);
      setUserProfile(`http://d1djtzszdq7pt7.cloudfront.net/${teacherId}/${r.data.data.userProfileImageLink}`);
      console.log('name!!!', name);
    });
  }, [name, setAbout, setEmail, setName, teacherId]);

  const onUpdateSubmit = useCallback(
    (e) => {
      e.preventDefault();
      setSaveSuccess(false);
      setLoading(true);
      const formData = new FormData();
      const teacherInfo = {
        name: name,
        email: email,
        about: about,
      };
      if (file !== undefined) {
        formData.append('file', file);
        formData.append('key', new Blob([JSON.stringify(teacherInfo)], { type: 'application/json' }));
        console.log('formData', formData);
        axios
          .put(`http://localhost:8000/api/v1/admin/teacher/${teacherId}`, formData, {
            withCredentials: true,
            headers: {
              'Content-Type': `multipart/form-data`,
            },
          })
          .then((response) => {
            setSaveSuccess(true);
            history.push('/teacher/' + response.headers.location.slice(response.headers.location.lastIndexOf('/') + 1));
            setLoading(false);
          })
          .catch((error) => {
            setSaveError(error.response);
            setLoading(false);
          });
      } else {
        console.log('problem');
      }
    },
    [about, email, file, history, name, teacherId],
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

  return (
    <RegisterTeacherContainer>
      <Form onSubmit={onUpdateSubmit}>
        <Label>
          <ImgContainer>
            <ImgPreview src={userProfile} />
          </ImgContainer>
          <span>Profile Image</span>
          <input type="file" accept="image/*" name="userProfileImageLink" onChange={imageHandler} />
        </Label>
        <Label id="name-label">
          <span>Name </span>
          <div>
            <Input type="name" name="name" value={name} onChange={onChangeTeacherName} required={true} />
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
      {loading ? <Loading /> : ''}
    </RegisterTeacherContainer>
  );
};

export default TeacherUpdate;
