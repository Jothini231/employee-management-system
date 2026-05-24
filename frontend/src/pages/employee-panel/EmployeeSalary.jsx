import React, { useState, useEffect, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { getSalariesByEmployeeId } from '../../services/salaryService';
import { FaDownload } from 'react-icons/fa';

const EmployeeSalary = () => {
  const { user } = useContext(AuthContext);
  const [salaries, setSalaries] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSalaries = async () => {
      try {
        if (user && user.id) {
          const res = await getSalariesByEmployeeId(user.id);
          if (res.data && res.data.success) {
            setSalaries(res.data.data);
          }
        }
      } catch (error) {
        console.error("Error fetching salaries:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchSalaries();
  }, [user]);

  const handleDownloadPayslip = (salary) => {
    // Generate a simple printable view
    const printWindow = window.open('', '_blank');
    printWindow.document.write(`
      <html>
        <head>
          <title>Payslip - ${salary.month}</title>
          <style>
            body { font-family: Arial, sans-serif; padding: 40px; }
            .header { text-align: center; margin-bottom: 30px; border-bottom: 2px solid #ccc; padding-bottom: 20px; }
            .content { line-height: 1.8; font-size: 16px; }
            .row { display: flex; justify-content: space-between; max-width: 400px; margin: auto; }
            .total { font-weight: bold; font-size: 18px; border-top: 1px solid #ccc; padding-top: 10px; margin-top: 10px;}
          </style>
        </head>
        <body>
          <div class="header">
            <h2>Payslip</h2>
            <h3>Month: ${salary.month}</h3>
          </div>
          <div class="content">
            <div class="row"><span>Employee Name:</span> <span>${user?.name}</span></div>
            <div class="row"><span>Email:</span> <span>${user?.email}</span></div>
            <br />
            <div class="row"><span>Basic Salary:</span> <span>$${salary.basicSalary}</span></div>
            <div class="row"><span>Allowance:</span> <span>$${salary.allowance}</span></div>
            <div class="row"><span>Bonus:</span> <span>$${salary.bonus}</span></div>
            <div class="row"><span>Deduction:</span> <span>-$${salary.deduction}</span></div>
            <div class="row total"><span>Net Salary:</span> <span>$${salary.netSalary}</span></div>
          </div>
          <script>
            window.onload = () => { window.print(); }
          </script>
        </body>
      </html>
    `);
    printWindow.document.close();
  };

  return (
    <div className="p-6 max-w-6xl mx-auto">
      <h2 className="text-2xl font-bold mb-6 text-[#1E2A38]">My Salary Records</h2>

      {loading ? (
        <p>Loading salaries...</p>
      ) : salaries.length === 0 ? (
        <div className="bg-white p-6 rounded shadow text-gray-500">No salary records found.</div>
      ) : (
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
          <div className="p-6 border-b border-gray-100 flex justify-between items-center">
            <h2 className="text-lg font-semibold text-gray-800">
              Salary History
            </h2>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left text-gray-500">
              <thead className="text-xs text-gray-700 uppercase bg-gray-50 border-b border-gray-100">
                <tr>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Month</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Basic Salary</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Allowance</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Bonus</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Deduction</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900">Net Salary</th>
                  <th scope="col" className="px-6 py-4 font-medium text-gray-900 text-center">Action</th>
                </tr>
              </thead>
              <tbody>
                {salaries.map((salary) => (
                  <tr key={salary.id} className="bg-white border-b border-gray-200 hover:bg-gray-50 transition-colors">
                    <td className="px-6 py-4 font-medium text-gray-900">{salary.month}</td>
                    <td className="px-6 py-4">${salary.basicSalary}</td>
                    <td className="px-6 py-4">${salary.allowance}</td>
                    <td className="px-6 py-4">${salary.bonus}</td>
                    <td className="px-6 py-4 text-red-500">-${salary.deduction}</td>
                    <td className="px-6 py-4 font-bold text-green-600">${salary.netSalary}</td>
                    <td className="px-6 py-4 text-center">
                      <button
                        onClick={() => handleDownloadPayslip(salary)}
                        className="bg-indigo-600 hover:bg-indigo-700 text-white p-2 rounded-lg transition-colors inline-flex items-center text-xs"
                        title="Download Payslip"
                      >
                        <FaDownload className="mr-2" /> Download
                      </button>
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

export default EmployeeSalary;
