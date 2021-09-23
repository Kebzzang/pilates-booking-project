import React from 'react';
import { Redirect, Switch, Route } from 'react-router-dom';
import './App.css';
import loadable from '@loadable/component';

const LogIn = loadable(() => import('./pages/login/LogIn'));
const SignUp = loadable(() => import('./pages/signup/SignUp'));
const Header = loadable(() => import('./layouts/Main/Header'));
const App = () => {
  return (
    <Switch>
      <Redirect exact path="/" to="/login" />
      <Route path="/login" component={LogIn} />
      <Route path="/signup" component={SignUp} />
      <Header />
    </Switch>
  );
};
export default App;
