import React from 'react';
import { Redirect, Switch, Route } from 'react-router-dom';
import './App.css';
import loadable from '@loadable/component';

const LogIn = loadable(() => import('./pages/login/LogIn'));
const SignUp = loadable(() => import('./pages/signup/SignUp'));
const Main = loadable(() => import('./layouts/Main/Main'));

const App = () => {
  return (
    <Switch>
      <Route exact path="/">
        <Redirect to="/login" />
      </Route>
      <Route path="/home" component={Main} />

      <Route path="/login" component={LogIn} />
      <Route path="/signup" component={SignUp} />
      {/*<Route path="/main/dm" component={DM} />*/}
      {/*<Route path="/main/channel" component={Calendar} />*/}
    </Switch>
  );
};

export default App;
