import React, { FC, useCallback, useState } from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import axios from 'axios';
import { Redirect, Link, NavLink, Route, Router, Switch } from 'react-router-dom';
import { ProfileModal, LogOutButton } from './style';
import Menu from '../../components/Menu/Menu';
import { Button, Nav, Navbar } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './style.css';
import loadable from '@loadable/component';
import TeacherCardDetail from '../../components/Teachers/TeacherCardDetail';
import TeacherUpdateForm from '../../components/Teachers/TeacherUpdateForm';
import TeacherSaveForm from '../../components/Teachers/TeacherSaveForm';

const Lessons = loadable(() => import('../../components/Calendar/Lessons'));
const Teachers = loadable(() => import('../../components/Teachers/TeacherList'));
const Members = loadable(() => import('../../components/Member/Members'));
const MemberDetail = loadable(() => import('../../components/Member/MemberDetail'));
const Header: FC = ({ children }) => {
  const [showUserMenu, setShowUserMenu] = useState(false);

  const { data, error, revalidate, mutate } = useSWR('http://localhost:8000/api/v1/user/me', fetcher, {
    dedupingInterval: 2000,
  }); //내가 원할 때 요청하기!!
  const onLogout = useCallback(() => {
    axios
      .post('http://localhost:8000/api/v1/logout', null, {
        withCredentials: true,
      })
      .then((response) => {
        mutate(false);
      });
  }, []);

  const onClickUserProfile = useCallback((e) => {
    e.stopPropagation();
    setShowUserMenu((prev) => !prev);
  }, []);

  if (!data) {
    return <Redirect to="/login" />;
  }
  return (
    <div>
      <Navbar sticky="top" bg="light" expand="lg">
        <Navbar.Brand>
          <NavLink
            className="main-nav"
            to="/"
            style={{
              color: '#05495e',
              fontSize: '110%',
              fontFamily: 'lobster',
              fontWeight: 'bolder',
            }}
          >
            Pilas
          </NavLink>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="m-auto">
            <NavLink className="main-nav" activeClassName="main-nav-active" to="/lessons">
              Lessons
            </NavLink>{' '}
            <NavLink className="main-nav" activeClassName="main-nav-active" to="/members">
              Members
            </NavLink>{' '}
            <NavLink className="main-nav" activeClassName="main-nav-active" to="/teachers">
              Instructors
            </NavLink>
          </Nav>
          <Nav>
            <span
              style={{ paddingLeft: '0px', width: '50px', border: 'none', backgroundColor: 'transparent' }}
              onClick={onClickUserProfile}
            >
              {data.username}
              {showUserMenu && (
                <Menu style={{ right: 0, top: 38 }} show={showUserMenu} onCloseModal={onClickUserProfile}>
                  <ProfileModal>
                    <div>
                      <span id="profile-name">{data.username}</span>
                      <span id="profile-active">Active</span>
                    </div>
                  </ProfileModal>
                  <LogOutButton onClick={onLogout}>Logout</LogOutButton>
                </Menu>
              )}
            </span>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
      <Switch>
        <Route exact path="/lessons" component={Lessons} />
        <Route exact path="/members" component={Members} />
        <Route exact path="/members/:memberId" component={MemberDetail} />
        <Route exact path="/teachers" component={Teachers} />
        <Route exact path="/teachers/save" component={TeacherSaveForm} />
        <Route exact path="/teachers/:teacherId" component={TeacherCardDetail} />
        <Route exact path="/teachers/:teacherId/update" component={TeacherUpdateForm} />
      </Switch>
    </div>
  );
};
export default Header;
