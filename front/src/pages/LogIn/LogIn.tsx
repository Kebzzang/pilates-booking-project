import React, { useCallback, useState } from 'react';
import useInput from '../../hooks/useInput';
import { Form, Label, Header, Input, LinkContainer, Button, Error } from '../signup/style';
import axios from 'axios';
import { Link, Redirect } from 'react-router-dom';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';

const LogIn = () => {
  const { data, error, revalidate } = useSWR('http://localhost:8080/api/v1/user/me', fetcher, {
    dedupingInterval: 100000, //주기적으로 호출은 되지만, 이 기간 내에는 캐시에서 불러오게 한다.
  }); //내가 원할 때 요청하기!!
  const [logInError, setLogInError] = useState(false);
  const [username, onChangeUsername] = useInput('');
  const [password, onChangePassword] = useInput('');

  const onSubmit = useCallback(
    (e) => {
      e.preventDefault();
      axios
        .post(
          'http://localhost:8080/api/v1/auth',
          {
            username,
            password,
          },
          { withCredentials: true },
        )
        .then((response) => {
          console.log(response.data);
          revalidate();
          //로그인 성공시
        })
        .catch((error) => {
          setLogInError(error.response?.data?.statusCode === 401);
          console.log(logInError);
          //로그인 실패시
        });
    },
    [username, password],
  );
  if (!error && data) {
    console.log('로그인됨', data);
    return <Redirect to="/main" />;
  }
  return (
    <div id="container">
      <Header>Pilas</Header>
      <Form onSubmit={onSubmit}>
        <Label id="email-label">
          <span>Username</span>
          <div>
            <Input type="text" id="email" name="email" value={username} onChange={onChangeUsername} />
          </div>
        </Label>
        <Label id="password-label">
          <span>Password</span>
          <div>
            <Input type="password" id="password" name="password" value={password} onChange={onChangePassword} />
          </div>
          {logInError && <Error>Username & Password doesn't match</Error>}
        </Label>
        <Button type="submit">LogIn</Button>
      </Form>
      <LinkContainer>
        Don't you have account?&nbsp;
        <Link to="/signup">Sign Up</Link>
      </LinkContainer>
    </div>
  );
};

export default LogIn;
