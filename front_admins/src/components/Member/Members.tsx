import React, { useCallback, useEffect, useState } from 'react';

import { VerticalTimeline } from 'react-vertical-timeline-component';
import useInput from '../../hooks/useInput';
import useSWR from 'swr';

import { Badge, Table } from 'react-bootstrap';
import DataFetcher from '../../utils/DataFetcher';
import { ITeacher, IUser } from '../../types/db';
import Loading from '../../layouts/Loading';
// 회원 관리 메뉴

const Members = () => {
  //데이터 끌고 오기 유저 리스트 쫙
  const { data: users } = useSWR('http://localhost:8000/api/v1/admin/user', DataFetcher);
  //유저 롤 변경 -> ROLE_ADMIN, ROLE_TEACHER, ROLE_TEACHER
  const [mySearch, onChangeMySearch] = useInput(''); //유저 검색용
  const [myCategory, setMyCategory] = useState('ROLE_USER'); //ROLE_ADMIN, ROLE_TEACHER, ROLE_USER
  if (!users) {
    return <Loading />;
  }
  function searchTeacher(Users: IUser[]) {
    let lowerMySearch = mySearch.toLowerCase();
    return Users.filter(
      (
        User, //이름과 이메일로 찾기
      ) =>
        User.username.toLowerCase().indexOf(lowerMySearch) > -1 || User.email.toLowerCase().indexOf(lowerMySearch) > -1,
    );
  }
  function selectWorking(value: string) {
    setMyCategory(value);
    console.log(myCategory);
  }
  return (
    <div style={{ width: '700px', marginLeft: 'auto', marginTop: '20px', marginRight: 'auto' }}>
      <Table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
            <th>Email</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {users.data.map((element: IUser, index: number) => (
            <tr>
              <td>{element.username}</td>
              <td>
                <Badge pill variant="secondary">
                  {element.role.slice(5)}
                </Badge>
              </td>
              <td>{element.email}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default Members;
