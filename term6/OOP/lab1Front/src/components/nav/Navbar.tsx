import React from 'react';
import {NavLink} from "react-router-dom";
import {useAuth0} from "@auth0/auth0-react";

const Navbar = ({cartCount}: { cartCount?: number }) => {
    const {logout, isAuthenticated} = useAuth0();

    return (
        <nav style={{
            display: 'flex',
            justifyContent: 'space-between',
            padding: '0 3rem',
            margin: '1rem 0',
            alignItems: 'center',
            background: '#219c58',
            borderRadius: '10px',
            color: 'white'
        }}>
            <NavLink to={'/'} style={{cursor: 'pointer', textDecoration: 'none', color: 'white'}}>
                <h1>Travel</h1>
            </NavLink>
            <div style={{display: 'flex', gap: '3rem', fontWeight: 'bold'}}>
                {
                    cartCount &&
                    <NavLink to={'/cart'} style={{cursor: 'pointer', textDecoration: 'none', color: 'white'}}>
                        Card [{cartCount}]
                    </NavLink>
                }
                {
                    isAuthenticated &&
                    <NavLink to={'/login'} onClick={() => logout({logoutParams: {returnTo: window.location.origin}})}
                             style={{cursor: 'pointer', textDecoration: 'none', color: 'white'}}>
                        Logout
                    </NavLink>
                }
                {
                    !isAuthenticated &&
                    <NavLink to={'/login'} style={{cursor: 'pointer', textDecoration: 'none', color: 'white'}}>
                        Login
                    </NavLink>
                }
            </div>
        </nav>
    );
};

export default Navbar;
