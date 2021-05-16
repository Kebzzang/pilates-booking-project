import React, { FC, useCallback, useState } from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import axios from 'axios';
import { Redirect, Link, NavLink, Route, Router, Switch } from 'react-router-dom';
import { ProfileModal, LogOutButton } from './style';
import Menu from '../../components/Menu/Menu';
import { Nav, Navbar } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './style.css';
import loadable from '@loadable/component';

const Calendar = loadable(() => import('../../components/Calendar/Calendar'));
const Teachers = loadable(() => import('../../components/Teachers'));
const Home = loadable(() => import('../../components/Home'));
const Me = loadable(() => import('../../components/Me'));
const Main: FC = ({ children }) => {
  const [showUserMenu, setShowUserMenu] = useState(false);

  const { data, error, revalidate, mutate } = useSWR('http://localhost:8080/api/v1/user/me', fetcher, {
    dedupingInterval: 2000,
  }); //내가 원할 때 요청하기!!
  const onLogout = useCallback(() => {
    axios
      .post('http://localhost:8080/api/v1/logout', null, {
        withCredentials: true,
      })
      .then((response) => {
        mutate(false);
      });
  }, []);
  const onInfos = useCallback(() => {
    axios
      .get('http://localhost:8080/api/v1/course/me/{id}', { withCredentials: true, params: { id: data.id } })
      .then((response) => {
        console.log(response);
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
            <NavLink className="main-nav" activeClassName="main-nav-active" exact to="/home/teachers">
              Teachers
            </NavLink>{' '}
            <NavLink className="main-nav" activeClassName="main-nav-active" exact to="/home/me">
              My Info
            </NavLink>
          </Nav>
          <Nav>
            <div onClick={onClickUserProfile}>
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
            </div>
          </Nav>
        </Navbar.Collapse>
      </Navbar>

      <Switch>
        <Route exact path="/home" component={Home} />
        <Route exact path="/home/booking" component={Calendar} />
        <Route exact path="/home/teachers" component={Teachers} />
        <Route exact path="/home/me" component={Me} />
      </Switch>
    </div>
  );
};
export default Main;
