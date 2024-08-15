
// components/Customers.tsx
import React from 'react';
import DataTable from './DataTable';

const Customers: React.FC = () => {
    const columns = ['Id', 'Name', 'Email', 'Phone'];
    const data = [
        { id: 1, name: 'John Doe', email: 'john@example.com', phone: '123-456-7890' },
        { id: 2, name: 'Jane Smith', email: 'jane@example.com', phone: '098-765-4321' },
    ];

    return (
        <>
            <h1>Customers</h1>
            <DataTable columns={columns} data={data} />
        </>
    );
};

export default Customers;

