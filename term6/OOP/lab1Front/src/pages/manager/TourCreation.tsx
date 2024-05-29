import React, {useState} from 'react';
import Navbar from "../../components/nav/Navbar";
import axios from "axios";

type TourType = 'Resort' | 'Excursion' | 'Shopping';

interface Tour {
    name: string;
    description: string;
    price: number;
    type: TourType;
    date: string;
    hot: boolean;
    city: string;
    tourCompanyId: number;
}

const TourCreation = () => {
    const [tour, setTour] = useState<Tour>({
        name: '',
        description: '',
        price: 0,
        type: 'Resort',
        date: '',
        hot: false,
        city: '',
        tourCompanyId: Number(localStorage.getItem('tourCompanyId'))
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setTour({
            ...tour,
            [e.target.name]: e.target.value,
        });
    };

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setTour({
            ...tour,
            [e.target.name]: e.target.checked,
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await axios.post('/tour-agent', tour);
            alert('Tour created successfully!');
        } catch (error) {
            alert('Failed to create tour');
            console.error(error);
        }
    };

    return (
        <div className="main-container">
            <Navbar/>
            <form onSubmit={handleSubmit}
                  style={{color: '#219c58', display: 'flex', flexDirection: 'column', maxWidth: '30rem', margin: '0 auto', gap: '1rem'}}>
                <input type="text" name="name" onChange={handleInputChange} placeholder="Name" required/>
                <input type="text" name="description" onChange={handleInputChange} placeholder="Description" required/>
                <input type="number" name="price" onChange={handleInputChange} placeholder="Price" required/>
                <select name="type" onChange={handleInputChange}>
                    <option value="Resort">Resort</option>
                    <option value="Excursion">Excursion</option>
                    <option value="Shopping">Shopping</option>
                </select>
                <input type="date" name="date" onChange={handleInputChange} required/>
                <div>
                    <label>Hot</label>
                    <input type="checkbox" name="hot" onChange={handleCheckboxChange}/>
                </div>
                <input type="text" name="city" onChange={handleInputChange} placeholder="City" required/>
                <button type="submit">Create tour</button>
            </form>
        </div>
    );
};

export default TourCreation;
