import React from 'react';
import TourCard from './TourCard';
import {Tour} from "../../model/Tour";

const TourList = ({onAddToCart, isInCard}: { onAddToCart?: any, isInCard?: boolean }) => {
    const tours: Tour[] = [
        {
            id: 1,
            image: 'https://www.trolleytours.com/wp-content/uploads/2016/07/trolley-tours-of-key-west.jpg',
            title: 'Tour 1',
            type: 'відпочинок',
            company: 'Company 1',
            discount: 10,
            isHot: true,
        },
        {
            id: 2,
            image: 'https://www.francetourisme.fr/media/PCT_TOOTBUS_JOUR/toot-bus-Hop-on-hop-off-2.jpg',
            title: 'Tour 2',
            type: 'екскурсія',
            company: 'Company 2',
            isHot: false,
        },
        {
            id: 3,
            image: 'https://media.tacdn.com/media/attractions-splice-spp-674x446/0b/cf/e5/cb.jpg',
            title: 'Tour 3',
            type: 'шопінг',
            company: 'Company 3',
            discount: 5,
            isHot: true,
        },
        {
            id: 1,
            image: 'https://www.trolleytours.com/wp-content/uploads/2016/07/trolley-tours-of-key-west.jpg',
            title: 'Tour 1',
            type: 'відпочинок',
            company: 'Company 1',
            discount: 10,
            isHot: true,
        },
        {
            id: 2,
            image: 'https://www.francetourisme.fr/media/PCT_TOOTBUS_JOUR/toot-bus-Hop-on-hop-off-2.jpg',
            title: 'Tour 2',
            type: 'екскурсія',
            company: 'Company 2',
            isHot: false,
        },
        {
            id: 3,
            image: 'https://media.tacdn.com/media/attractions-splice-spp-674x446/0b/cf/e5/cb.jpg',
            title: 'Tour 3',
            type: 'шопінг',
            company: 'Company 3',
            discount: 5,
            isHot: true,
        },
    ];

    return (
        <div style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            maxWidth: '1100px',
            margin: '0 auto'
        }}>
            {tours.map((tour) => (
                <TourCard key={tour.id} tour={tour} onAddToCart={onAddToCart} isInCard={isInCard}/>
            ))}
        </div>
    );
};

export default TourList;
