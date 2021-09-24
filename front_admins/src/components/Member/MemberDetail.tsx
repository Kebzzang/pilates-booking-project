import React, { FC } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { DetailDiv, RegisterTeacherContainer } from '../Teachers/style/SaveStyle';
import { IUser } from '../../types/db';

const MemberDetail = () => {
  const { memberId } = useParams<{ memberId: string }>();
  const { state } = useLocation<IUser>();
  return (
    <RegisterTeacherContainer>
      <DetailDiv>{state.username}</DetailDiv>
      <DetailDiv>{state.email}</DetailDiv>
      <DetailDiv>{state.role}</DetailDiv>
    </RegisterTeacherContainer>
  );
};

export default MemberDetail;
