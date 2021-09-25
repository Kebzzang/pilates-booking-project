import React, { useEffect, useState } from 'react';
import { RoleBadge } from './style';
import useInput from '../../hooks/useInput';
import useSWR from 'swr';

import { Button, Dropdown, DropdownButton, FormControl, Table } from 'react-bootstrap';
import DataFetcher from '../../utils/DataFetcher';
import { IUser } from '../../types/db';
import Loading from '../../layouts/Loading';
import InputGroup from 'react-bootstrap/InputGroup';
import axios from 'axios';
import { GiHamburgerMenu } from 'react-icons/all';
import { Link } from 'react-router-dom';
import MemberListItem from './MemberListItem';
// 회원 관리 메뉴

const MemberList = () => {
  //데이터 끌고 오기 유저 리스트 쫙
  const { data: users } = useSWR('http://localhost:8000/api/v1/admin/user', DataFetcher);
  //유저 롤 변경 -> ROLE_ADMIN, ROLE_TEACHER, ROLE_TEACHER
  const [filteredMembers, setFilteredMembers] = useState<IUser[]>([]);
  const [mySearch, onChangeMySearch] = useInput(''); //유저 검색용
  const [myCategory, setMyCategory] = useState('All'); //ROLE_ADMIN, ROLE_TEACHER, ROLE_USER

  useEffect(() => {
    if (users) {
      switch (myCategory) {
        case 'All':
          setFilteredMembers(users.data);
          break;
        case 'ADMIN':
          setFilteredMembers(users.data.filter((user: IUser) => user.role === 'ROLE_ADMIN'));
          break;
        case 'TEACHER':
          setFilteredMembers(users.data.filter((user: IUser) => user.role === 'ROLE_TEACHER'));
          break;
        case 'USER':
          setFilteredMembers(users.data.filter((user: IUser) => user.role === 'ROLE_USER'));
          break;
      }
    }
  }, [myCategory, users]);

  function searchUser(Users: IUser[]) {
    let lowerMySearch = mySearch.toLowerCase();
    return Users.filter(
      (
        User, //이름과 이메일로 찾기
      ) =>
        User.username.toLowerCase().indexOf(lowerMySearch) > -1 || User.email.toLowerCase().indexOf(lowerMySearch) > -1,
    );
  }
  function selectRole(value: string) {
    setMyCategory(value);
  }
  if (!users) {
    return <Loading />;
  }

  return (
    <div style={{ width: '700px', marginLeft: 'auto', marginTop: '20px', marginRight: 'auto' }}>
      <InputGroup
        className="mb-3"
        style={{ width: '400px', marginLeft: 'auto', marginTop: '20px', marginRight: 'auto' }}
      >
        <FormControl onChange={onChangeMySearch} aria-label="Text input with dropdown button" placeholder="Search" />
        <DropdownButton variant="outline-secondary" title={myCategory} id="input-group-dropdown-2">
          <Dropdown.Item id="All" onClick={(e) => selectRole(e.currentTarget.id)}>
            all
          </Dropdown.Item>
          <Dropdown.Item id="ADMIN" onClick={(e) => selectRole(e.currentTarget.id)}>
            Admin
          </Dropdown.Item>
          <Dropdown.Item id="TEACHER" onClick={(e) => selectRole(e.currentTarget.id)}>
            Teacher
          </Dropdown.Item>
          <Dropdown.Item id="USER" onClick={(e) => selectRole(e.currentTarget.id)}>
            User
          </Dropdown.Item>
        </DropdownButton>
      </InputGroup>
      <Table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
            <th>Email</th>
            <th />
          </tr>
        </thead>
        <tbody>
          {searchUser(filteredMembers).map((element: IUser) => (
            <MemberListItem element={element} />
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default MemberList;
