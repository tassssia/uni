import React from 'react';
import "../../assets/style/manager.css"
import "../../assets/style/common.css"
import ManagerControls from "../../components/manager/ManagerControls";
import Navbar from "../../components/nav/Navbar";

const Manager = () => {
    return (
        <div className="main-container">
            <Navbar/>
            <ManagerControls/>
        </div>
    );
};

export default Manager;