import React, { useState, useEffect, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { getLeaveByEmployee } from '../../services/leaveService';
import { useNavigate } from 'react-router-dom';

const EmployeeLeaves = () => {
  const { user } = useContext(AuthContext);
  const [leaves, setLeaves] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchLeaves = async () => {
      try {
        if (user && user.id) {
          const res = await getLeaveByEmployee(user.id);
          if (res.data && res.data.success) {
            setLeaves(res.data.data);
          }
        }
      } catch (error) {
        console.error("Error fetching leaves:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchLeaves();
  }, [user]);

  return (
    <div className="p-6 max-w-6xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-[#1E2A38]">My Leaves</h2>
        <button
          onClick={() => navigate('/apply-leave')}
          className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded transition-colors"
        >
          Apply for Leave
        </button>
      </div>

      {loading ? (
        <p>Loading your leaves...</p>
      ) : leaves.length === 0 ? (
        <div className="bg-white p-6 rounded shadow text-gray-500">You have not applied for any leaves yet.</div>
      ) : (
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
          <div className="p-6 border-b border-gray-100 flex justify-between items-center">
            <h2 className="text-lg font-semibold text-gray-800">
              My Leave Requests
            </h2>
          </div>
          
          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left text-gray-500">
              <thead className="text-xs text-gray-700 uppercase bg-gray-50 border-b border-gray-100">
                <tr>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Leave Type</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Duration</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Reason</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Status</th>
                </tr>
              </thead>
              <tbody>
                {leaves.map((leave) => (
                  <tr key={leave.id} className="bg-white border-b border-gray-200 hover:bg-gray-50 transition-colors">
                    <td className="px-6 py-4">
                      <span className="px-2.5 py-1 bg-gray-100 text-gray-700 rounded-md text-xs font-medium">
                        {leave.leaveType}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {leave.startDate} to {leave.endDate}
                    </td>
                    <td className="px-6 py-4 max-w-xs truncate">
                      {leave.reason}
                    </td>
                    <td className="px-6 py-4">
                      <span
                        className={`inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium border ${
                          leave.status === 'APPROVED'
                            ? 'bg-green-50 text-green-700 border-green-200'
                            : leave.status === 'REJECTED'
                            ? 'bg-red-50 text-red-700 border-red-200'
                            : 'bg-yellow-50 text-yellow-700 border-yellow-200'
                        }`}
                      >
                        {leave.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default EmployeeLeaves;
