// components/Transactions.tsx
import React from 'react';
import DataTable from './DataTable';

const Transactions: React.FC = () => {
    const columns = ['Id', 'Date', 'Amount', 'Customer'];
    const data = [
        { id: 1, date: '2023-08-01', amount: 100, customer: 'John Doe' },
        { id: 2, date: '2023-08-02', amount: 200, customer: 'Jane Smith' },
    ];

    return (
        <>
            <h1>Transactions</h1>
            <DataTable columns={columns} data={data} />
        </>
    );
};

export default Transactions;
