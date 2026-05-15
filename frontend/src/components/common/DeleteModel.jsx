import { FaTrashAlt, FaTimes } from "react-icons/fa";

function DeleteModal({ name, onCancel, onConfirm }) {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm px-4">
      
      <div className="w-full max-w-md bg-white rounded-2xl shadow-2xl border border-gray-100 overflow-hidden animate-in fade-in zoom-in duration-200">
        
        {/* Header */}
        <div className="flex items-center gap-3 px-6 pt-6 border-b border-gray-100">
          <div className="w-10 h-10 rounded-full bg-red-100 flex items-center justify-center text-red-600">
            <FaTrashAlt className="text-sm" />
          </div>

          <div>
            <h2 className="text-lg font-bold text-gray-800">
              Delete Record
            </h2>
          </div>
        </div>

        {/* Body */}
        <div className="px-6 py-3">
          <p className="text-gray-600 leading-relaxed">
            Are you sure you want to delete
            <span className="font-semibold text-gray-800">
              {name}
            </span>
            ?
          </p>
        </div>

        {/* Footer */}
        <div className="flex justify-end gap-3 px-6 py-4 bg-gray-50 border-t border-gray-100">
          
          <button
            onClick={onCancel}
            className="px-5 py-2.5 rounded-lg border border-gray-300 text-gray-700 hover:bg-gray-100 transition font-medium flex items-center gap-2"
          >
            
            Cancel
          </button>

          <button
            onClick={onConfirm}
            className="px-5 py-2.5 rounded-lg bg-red-600 hover:bg-red-700 text-white transition font-medium flex items-center gap-2 shadow-sm"
          >
           
            Delete
          </button>
        </div>
      </div>
    </div>
  );
}

export default DeleteModal;