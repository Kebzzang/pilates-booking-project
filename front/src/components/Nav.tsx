import React from "react";
import { Link } from "react-router-dom";

const Nav = () => {
  return (
    <nav className='navbar navbar-expand-md navbar-dark bg-dark mb-4'>
      <div className='container-fluid'>
        <Link className='navbar-brand' to='/'>
          Home
        </Link>

        <div>
          <ul className='navbar-nav me-auto mb-2 mb-md-0'>
            <li className='nav-item'>
              <Link className='nav-link active' aria-current='page' to='/login'>
                Login
              </Link>
            </li>
            <li className='nav-item'>
              <Link
                className='nav-link active'
                aria-current='page'
                to='/register'
              >
                Register
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Nav;
