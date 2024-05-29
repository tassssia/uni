import React, {useState} from 'react';
import {Client, TourDetail} from "../../model/Tour";
import "../../assets/style/manager.css"

const ManagerControls = () => {
    const clients: Client[] = [
        {id: 1, name: 'Client A', discount: 0},
        {id: 2, name: 'Client B', discount: 0},
    ];

    const tours: TourDetail[] = [
        {id: 1, title: 'Tour 1', isHot: false},
        {id: 2, title: 'Tour 2', isHot: false},
    ];

    const [clientList, setClientList] = useState(clients);
    const [tourList, setTourList] = useState(tours);

    const handleClientDiscountChange = (id: number, discount: number) => {
        const updatedClients = clientList.map((client) =>
            client.id === id ? {...client, discount} : client
        );
        setClientList(updatedClients);
    };

    const handleTourHotToggle = (id: number) => {
        const updatedTours = tourList.map((tour) =>
            tour.id === id ? {...tour, isHot: !tour.isHot} : tour
        );
        setTourList(updatedTours);
    };

    return (
        <div className="Manager">
            <h2>Client Discounts</h2>
            {clientList.map((client) => (
                <div key={client.id} className="ClientCard">
                    <h4>{client.name}</h4>
                    <input
                        type="number"
                        min="0"
                        max="100"
                        value={client.discount}
                        onChange={(e) => handleClientDiscountChange(client.id, Number(e.target.value))}
                    />
                </div>
            ))}

            <h2>Hot Tours</h2>
            {tourList.map((tour) => (
                <div key={tour.id} className="TourCard">
                    <h4>{tour.title}</h4>
                    <input
                        type="checkbox"
                        checked={tour.isHot}
                        onChange={() => handleTourHotToggle(tour.id)}
                    />
                </div>
            ))}
            <button id='save-manager-form'>Save</button>
        </div>
    );
};

export default ManagerControls;