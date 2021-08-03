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

const Calendar = loadable(() => import('../../components/Calendar/Calendar'));
const Home = loadable(() => import('../../components/Home'));
const Teachers = loadable(() => import('../../components/Teachers/Teachers'));
const MyClass = loadable(() => import('../../components/BookedClasses/MyClass'));
const Main: FC = ({ children }) => {
  const [showUserMenu, setShowUserMenu] = useState(false);

  const { data, error, revalidate, mutate } = useSWR(
    'http://ec2-3-38-35-210.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/me',
    fetcher,
    {
      dedupingInterval: 2000,
    },
  ); //내가 원할 때 요청하기!!
  const onLogout = useCallback(() => {
    axios
      .post('http://ec2-3-38-35-210.ap-northeast-2.compute.amazonaws.com:8080/api/v1/logout', null, {
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
            to="/home"
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
            <NavLink className="main-nav" activeClassName="main-nav-active" exact to="/home/booking">
              Booking
            </NavLink>{' '}
            <NavLink className="main-nav" activeClassName="main-nav-active" exact to="/home/myclass">
              My Class
            </NavLink>{' '}
            <NavLink className="main-nav" activeClassName="main-nav-active" exact to="/home/teachers">
              Instructors
            </NavLink>
          </Nav>
          <Nav>
            <button
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
            </button>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
      <Switch>
        <Route exact path="/home" component={Home} />
        <Route exact path="/home/booking" component={Calendar} />
        <Route exact path="/home/myclass" component={MyClass} />
        <Route exact path="/home/teachers" component={Teachers} />
      </Switch>
    </div>
  );
};
export default Main;
