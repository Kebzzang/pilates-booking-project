import React, { FC, useCallback } from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import { Header, RightMenu } from './style';

const MainBar: FC = ({ children }) => {
  const { data, error, revalidate, mutate } = useSWR('http://localhost:8080/api/v1/user/me', fetcher); //내가 원할 때 요청하기!!
  const onLogout = useCallback(() => {
    axios
      .post('http://localhost:8080/api/v1/logout', null, {
        withCredentials: true,
      })
      .then((response) => {
        mutate(false);
      });
  }, []);
  console.log('1', data);
  if (!data) {
    return <Redirect to="/" />;
  }
  return (
    <div>
      <Header>
        <RightMenu>
          <span>test</span>
        </RightMenu>
      </Header>
      <button onClick={onLogout}>로그아웃!!</button>
      {children}
    </div>
  );
};

export default MainBar;
