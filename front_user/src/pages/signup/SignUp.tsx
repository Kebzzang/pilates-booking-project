import React, { useCallback, useState } from 'react';
import { Form, Label, Header, Input, LinkContainer, Button, Error, Container, Container2 } from './style';
import useInput from '../../hooks/useInput';
import axios from 'axios';
import { Redirect, Link } from 'react-router-dom';

import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import Loading from '../../layouts/Loading';

const SignUp = () => {
  const { data } = useSWR('http://localhost::8000/api/v1/user/me', fetcher);

  const [username, onChangeUsername] = useInput('');
  const [email, onChangeEmail] = useInput('');
  const [password, , setPassword] = useInput('');
  const [passwordCheck, , setPasswordCheck] = useInput('');

  const [mismatchError, setMismatchError] = useState(false);
  const [signUpError, setSignUpError] = useState('');
  const [signUpSuccess, setSignUpSuccess] = useState(false);

  const onChangePassword = useCallback(
    (e) => {
      setPassword(e.target.value);
      setMismatchError(e.target.value !== passwordCheck);
    },
    [passwordCheck],
  );

  const onChangePasswordCheck = useCallback(
    (e) => {
      setPasswordCheck(e.target.value);
      setMismatchError(e.target.value !== password);
    },
    [password],
  );

  const onSubmit = useCallback(
    (e) => {
      e.preventDefault();
      //console.log(email, username, password);
      //   setLoading(true); //요청 시작 -> 로딩 시작 트루로 변경
      if (!mismatchError) {
        console.log('서버로 회원가입 gogo');
        setSignUpError('');
        setSignUpSuccess(false); //전에 했던 요청 데이터가 남아있지 않게 초기화해줌
        axios
          .post('http://localhost:8000/api/v1/signup', {
            username,
            email,
            password,
          })
          .then((response) => {
            console.log(response);
            setSignUpSuccess(true);
            //   setLoading(false); //요청 성공 로딩 끝
            alert('SignUp Success! Login Please');
          }) //성공
          .catch((error) => {
            // console.log(username, email, password);
            console.log(error.response.data.errorMsg);
            setSignUpError(error.response.data.errorMsg);
            //   setLoading(false); //에러 발생, 로딩 끝
          }) //실패
          .finally(() => {}); //무조건 실행
      }
    },
    [username, email, password, passwordCheck, mismatchError],
  );
  if (signUpSuccess) {
    return <Redirect to="/" />;
  }

  if (data) {
    return <Redirect to="/home" />;
  }
  return (
    <Container>
      <Container2>
        <Header>Join Pilas</Header>
        <Form onSubmit={onSubmit}>
          <Label id="nickname-label">
            <span>Username</span>
            <div>
              <Input type="text" id="nickname" name="nickname" value={username} onChange={onChangeUsername} />
            </div>
          </Label>
          {!username && <Error>유저네임을 입력해주세요.</Error>}
          {signUpError && <Error>{signUpError}</Error>}
          <Label id="email-label">
            <span>Email</span>
            <div>
              <Input type="email" id="email" name="email" value={email} onChange={onChangeEmail} />
            </div>
          </Label>
          {!email && <Error>이메일을 입력해주세요.</Error>}
          <Label id="password-label">
            <span>Password</span>
            <div>
              <Input type="password" id="password" name="password" value={password} onChange={onChangePassword} />
            </div>
          </Label>
          <Label id="password-check-label">
            <span>Confirm your Password</span>
            <div>
              <Input
                type="password"
                id="password-check"
                name="password-check"
                value={passwordCheck}
                onChange={onChangePasswordCheck}
              />
            </div>
            {mismatchError && <Error>비밀번호가 일치하지 않습니다.</Error>}

            {/*{signUpSuccess && <Success>회원가입되었습니다! 로그인해주세요.</Success>}*/}
          </Label>
          <Button type="submit">Create account</Button>
        </Form>
        <LinkContainer>
          Already have an account?&nbsp;
          <Link to="/login">Log In</Link>
        </LinkContainer>
        {/*{loading ? <Loading /> : ''}*/}
      </Container2>
    </Container>
  );
};

export default SignUp;
