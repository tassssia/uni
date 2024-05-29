import React from 'react';
import TourList from "../../components/main/TourList";
import Navbar from "../../components/nav/Navbar";

const Cart = () => {
    return (
        <div className={'main-container'}>
            <Navbar/>
            <TourList isInCard={true}></TourList>
        </div>
    )
}

export default Cart