import React, {useState} from 'react';
import "../../assets/style/login.css"
import "../../assets/style/common.css"
import {NavLink} from "react-router-dom";
import {useAuth0} from "@auth0/auth0-react";

const LoginPage = () => {
    const { loginWithRedirect } = useAuth0();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        // Do something with the username, password, and initial amount
    };

    return (
        <div className="container">
            <h1 className="title">Travel Lab OOP 1</h1>
            <form onSubmit={handleSubmit}>
                {/*<div className="form-group">*/}
                {/*    <label htmlFor="username">Username:</label>*/}
                {/*    <input type="text" id="username" value={username} onChange={handleUsernameChange}/>*/}
                {/*</div>*/}
                {/*<div className="form-group">*/}
                {/*    <label htmlFor="password">Password:</label>*/}
                {/*    <input type="password" id="password" value={password} onChange={handlePasswordChange}/>*/}
                {/*</div>*/}
                <button type="submit" className="submit-button" onClick={() => loginWithRedirect()}>Log In</button>
            </form>
            <NavLink to={'/register'} style={{
                cursor: 'pointer',
                textDecoration: 'none',
                color: '#219c58',
                marginTop: '1rem',
                textAlign: 'start'
            }}>
                Registration
            </NavLink>
        </div>
    );
};

export default LoginPage;
