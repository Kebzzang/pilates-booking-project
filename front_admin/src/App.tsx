import React from 'react';
import { Redirect, Switch, Route } from 'react-router-dom';
import './App.css';
import loadable from '@loadable/component';

const LogIn = loadable(() => import('./login/LogIn'));
const SignUp = loadable(() => import('./signup/SignUp'));

function App() {
  return (
    <Switch>
      <Route exact path="/">
        <Redirect to="/login" />
      </Route>
      <Route path="/login" component={LogIn} />
      <Route path="/signup" component={SignUp} />
    </Switch>
  );
}

export default App;
