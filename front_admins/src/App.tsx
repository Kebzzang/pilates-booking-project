import React from 'react';
import { Redirect, Switch, Route } from 'react-router-dom';
import './App.css';
import loadable from '@loadable/component';

const LogIn = loadable(() => import('./pages/login/LogIn'));
const SignUp = loadable(() => import('./pages/signup/SignUp'));
const Header = loadable(() => import('./layouts/Main/Header'));
const Teachers = loadable(() => import('./components/Teachers/Teachers'));
const MyClass = loadable(() => import('./components/BookedClasses/MyClass'));

const Calendar = loadable(() => import('./components/Calendar/Calendar'));
const App = () => {
  return (
    <Switch>
      {/*<Route exact path="/">*/}
      {/*  <Redirect to="/login" />*/}
      {/*</Route>*/}
      {/*<Route path="/home" component={Header} />*/}

      {/*<Route path="/login" component={LogIn} />*/}
      {/*<Route path="/signup" component={SignUp} />*/}
      <Redirect exact path="/" to="/login" />
      <Route path="/login" component={LogIn} />
      <Route path="/signup" component={SignUp} />
      <Header>
        <Route path="/book" component={Calendar} />
        <Route path="/teachers" component={Teachers} />
        <Route path="/myclass" component={MyClass} />
      </Header>
    </Switch>
  );
};
export default App;
