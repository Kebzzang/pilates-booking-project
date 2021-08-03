import React, { useCallback, useState } from 'react';
import useInput from '../hooks/useInput';
import { Form, Label, Header, Input, LinkContainer, Button, Error } from '../signup/style';
import axios from 'axios';
import { Link, Redirect } from 'react-router-dom';
import useSWR from 'swr';
import fetcher from '../utils/fetcher';
import Loading from '../layouts/Loading';

const LogIn = () => {
  const { data, error, mutate } = useSWR(
    'http://ec2-3-38-35-210.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/me',
    fetcher,
  );
  const [logInError, setLogInError] = useState(false);
  const [username, onChangeUsername] = useInput('');
  const [password, onChangePassword] = useInput('');

  const onSubmit = useCallback(
    (e) => {
      e.preventDefault();
      axios
        .post(
          //localhost:8000/api/v1/authadmin
          //http://ec2-3-38-35-210.ap-northeast-2.compute.amazonaws.com:8080/api/v1/authadmin
          'http://localhost:8000/api/v1/authadmin ',
          {
            username,
            password,
          },
          { withCredentials: true },
        )
        .then((response) => {
          mutate(response.data.data, false); //true면 optimistic ui - 다시 fetch 요청
          //나는 그냥 로그인 후 받아온 사용자 데이터를 저장하겠다. 어차피 페치해서 받아올 데이터도 똑같을 것이다.
          console.log(response.data.data);
        })
        .catch((error) => {
          //setLogInError(error.response?.data?.statusCode === 401);
          setLogInError(true);
          console.log(logInError);
        });
    },
    [username, password],
  );
  if (data === undefined) {
    return <Loading />;
  }
  if (!error && data) {
    console.log('로그인됨!!여기 때문에', data);
    return <Redirect to="/home/calendar" />;
  }
  return (
    <div id="container">
      <Header>Pilas</Header>

      <Form onSubmit={onSubmit}>
        <Label id="username-label">
          <span>Username</span>
          <div>
            <Input type="text" id="username" name="username" value={username} onChange={onChangeUsername} />
          </div>
        </Label>
        <Label id="password-label">
          <span>Password</span>
          <div>
            <Input type="password" id="password" name="password" value={password} onChange={onChangePassword} />
          </div>
          {logInError && <Error>You are not registered as admin. </Error>}
        </Label>
        <Button type="submit">LogIn as admin</Button>
      </Form>
      <LinkContainer>
        Don't you have admin account?&nbsp;
        <Link to="/signup">Sign Up</Link>
      </LinkContainer>
    </div>
  );
};

export default LogIn;
