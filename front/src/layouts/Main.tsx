import React, { FC, useCallback } from 'react';
import useSWR from 'swr';
import fetcher from '../utils/fetcher';
import axios from 'axios';
import { Redirect } from 'react-router-dom';

const Main: FC = ({ children }) => {
  const { data, error, revalidate } = useSWR('http://localhost:8080/api/v1/user/me', fetcher); //내가 원할 때 요청하기!!
  const onLogout = useCallback(() => {
    axios
      .post('http://localhost:8080/api/v1/logout', null, {
        withCredentials: true,
      })
      .then((response) => {
        revalidate().then((r) => console.log(r));
        console.log(response);
      });
  }, []);
  if (!data) {
    return <Redirect to="/" />;
  }
  return (
    <div>
      {data.result}
      {error}
      <button onClick={onLogout}>로그아웃!!</button>
      {children}
    </div>
  );
};

export default Main;
