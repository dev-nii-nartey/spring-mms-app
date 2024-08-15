
// components/ItemsInventory.tsx
// import React from 'react';
import DataTable from './DataTable';

const ItemsInventory = () => {
    const columns = ['Id', 'Name', 'Price', 'Category', 'Quantity'];
    const data = [
        { id: 1, name: 'Item 1', price: 10, category: 'Category A', quantity: 100 },
        { id: 2, name: 'Item 2', price: 20, category: 'Category B', quantity: 200 },
    ];

    return (
        <>
            <h1>Items Inventory</h1>
            <DataTable columns={columns} data={data} />
        </>
    );
};

export default ItemsInventory;
