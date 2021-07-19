package com.sumauto.cube

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sumauto.cube.databinding.FragmentImBinding
import com.sumauto.helper.utils.SystemUI
import com.sumauto.im.IM

class IMFragment : Fragment() {
    private var mBinding: FragmentImBinding? = null
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentImBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SystemUI.applyStatusBarHeight(view)
        mBinding?.apply {

            login11.setOnClickListener {
                userId = "DU1001468"

                login("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTM1NDI4MzksImlhdCI6MTYxMDk1MDgzOSwidWlkIjoiRFUxMDAxNDY4In0.MwO0XVZOcXOKg0HBCd9AZQ-9eZXsU5Moq1Bkqlejfmw")

            }
            login12.setOnClickListener {
                userId = "DU1001467"

                login("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTM1NDI4OTAsImlhdCI6MTYxMDk1MDg5MCwidWlkIjoiRFUxMDAxNDY3In0.K50CNIymaC3cm2c-GHteDbQC5_dpLvJTw5kRq1GZRGY")

            }
        }
        IM.setUp(requireContext())


    }

    private fun login(token:String){
        val dialog = ProgressDialog.show(requireActivity(), "Connecting...", null)
        IM.connect("http")
        dialog.dismiss()
    }

}