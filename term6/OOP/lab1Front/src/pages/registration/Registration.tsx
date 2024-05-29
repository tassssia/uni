import React, {useState} from 'react';
import "../../assets/style/login.css"
import {NavLink} from "react-router-dom";

const RegistrationPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [initialAmount, setInitialAmount] = useState('');
    const [role, setRole] = useState('');

    const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    };

    const handleInitialAmountChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setInitialAmount(event.target.value);
    };

    const handleRoleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setRole(event.target.value);
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        // Do something with the username, password, and initial amount
    };

    return (
        <div className="container">
            <h1 className="title">Travel Lab OOP 1</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input type="text" id="username" value={username} onChange={handleUsernameChange}/>
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input type="password" id="password" value={password} onChange={handlePasswordChange}/>
                </div>
                {
                    role !== "travel-manager" &&
                    <div className="form-group">
                        <label htmlFor="initial-amount">Initial Amount:</label>
                        <input type="number" id="initial-amount" value={initialAmount}
                               onChange={handleInitialAmountChange}/>
                    </div>
                }
                <div className="form-group">
                    <label htmlFor="role">Role:</label>
                    <select id="role" value={role} onChange={handleRoleChange}>
                        <option value="">Select a role</option>
                        <option value="user">User</option>
                        <option value="travel-manager">Travel Manager</option>
                    </select>
                </div>
                <button type="submit" className="submit-button">Register</button>
            </form>
            <NavLink to={'/login'} style={{
                cursor: 'pointer',
                textDecoration: 'none',
                color: '#219c58',
                marginTop: '1rem',
                textAlign: 'start'
            }}>
                Login
            </NavLink>
        </div>
    );
};

export default RegistrationPage;
