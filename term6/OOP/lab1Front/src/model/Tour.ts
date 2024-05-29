export type Tour = {
    id: number;
    image: string;
    title: string;
    type: string;
    company: string;
    discount?: number;
    isHot: boolean;
}

export type TourDetail = {
    id: number;
    title: string;
    isHot: boolean;
}

export type Client = {
    id: number;
    name: string;
    discount: number;
}