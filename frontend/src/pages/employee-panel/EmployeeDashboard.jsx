import React, { useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { FaUserCircle, FaCalendarAlt, FaMoneyBillWave } from 'react-icons/fa';

const EmployeeDashboard = () => {
  const { user } = useContext(AuthContext);

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Welcome, {user?.name || 'Employee'}!</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="bg-white rounded-lg shadow p-6 flex items-center border-l-4 border-indigo-500">
          <div className="rounded-full bg-indigo-100 p-3 mr-4">
            <FaUserCircle className="text-indigo-500 text-2xl" />
          </div>
          <div>
            <p className="text-sm text-gray-500 font-semibold uppercase">Profile Status</p>
            <p className="text-xl font-bold text-gray-800">Active</p>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow p-6 flex items-center border-l-4 border-green-500">
          <div className="rounded-full bg-green-100 p-3 mr-4">
            <FaMoneyBillWave className="text-green-500 text-2xl" />
          </div>
          <div>
            <p className="text-sm text-gray-500 font-semibold uppercase">Latest Salary</p>
            <p className="text-xl font-bold text-gray-800">Paid</p>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow p-6 flex items-center border-l-4 border-orange-500">
          <div className="rounded-full bg-orange-100 p-3 mr-4">
            <FaCalendarAlt className="text-orange-500 text-2xl" />
          </div>
          <div>
            <p className="text-sm text-gray-500 font-semibold uppercase">Leave Balance</p>
            <p className="text-xl font-bold text-gray-800">Available</p>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow p-6">
        <h3 className="text-lg font-bold text-gray-800 mb-4">Quick Actions</h3>
        <div className="flex gap-4">
          <a href="/my-leaves" className="px-4 py-2 bg-indigo-50 text-indigo-700 rounded hover:bg-indigo-100 transition-colors font-medium">Apply for Leave</a>
          <a href="/my-salary" className="px-4 py-2 bg-gray-50 text-gray-700 rounded hover:bg-gray-100 transition-colors font-medium">View Payslips</a>
        </div>
      </div>
    </div>
  );
};

export default EmployeeDashboard;
