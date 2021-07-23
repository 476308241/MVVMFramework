package com.finest.comm_net.request



import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*


interface HttpRequest {

    //    https://api.shenjian.io/
    @GET
    fun getList(@Url url: String): Observable<String>

    @POST("flow-review/clue/add")
    fun addClue(
        @Body params: RequestBody, @Query("nextUserId") nextUserId: String, @Query("juLeaderUserId") juLeaderUserId:String, @Query("loop") loop:Int
    ): Observable<String>

    @POST("flow-review/informationAnalyze/againEditSubmit")
    fun againEditSubmit(
        @Body params: RequestBody, @Query("nextUserId") nextUserId: String, @Query("juLeaderUserId") juLeaderUserId:String, @Query("loop") loop:Int
    ): Observable<String>


    @POST("flow-review/clue/getClue")
    fun getClueList(
        @Body params: RequestBody, @Query("pageNum") pageNum: Int
        , @Query("pageSize") pageSize: Int
    ): Observable<String>

    @GET("flow-review/informationAnalyze/getInformationAnalyzeByFlowId")
    fun getClueDetail(@Query("flowId") flowId: Int): Observable<String>

    @GET("flow-review/informationAnalyze/getInformationAnalyzeById")
    fun getClueDetailByClueId(@Query("id") id: Int): Observable<String>

    @GET("flow-review/informationAnalyze/getInformationAnalyzeById")
    fun getClueDetailForBK(@Query("id") id: Int): Observable<String>

    @POST("surveillance/querySurveillanceDetail")
    fun querySurveillanceDetail(@Body params: RequestBody): Observable<String>

    @GET("flow-review/case/getCase")
    fun getCaseList(@Query("pageNum") pageNum: Int, @Query("pageSize") pageSize: Int): Observable<String>

    @GET("flow-review/case/getCaseById")
    fun getCaseById(@Query("caseId") caseId: String): Observable<String>

    @POST("flow-review/flowProcessList/getFlowProcessList")
    fun getFlowProcessList(
        @Body params: RequestBody, @Query("pageNum") pageNum: Int, @Query("pageSize") pageSize: Int
    ): Observable<String>

    /**
     * 新案件列表相关接口
     */
//    @GET("flow-review/anjian/getAnJianList")
//    fun getCaseList(@Query("pageNum")pageNum:Int,@Query("pageSize")pageSize:Int): Observable<String>
//    @GET("flow-review/anjian/getAnJianById")
//    fun getCaseById(@Query("acLogicId")caseId:Int): Observable<String>

//    @GET("flow-review/flowProcessList/getFlowProcessByFlowId")
//    fun getFlowProcessByFlowId( @Query("flowId") flowId :Int
//    ): Observable<String>
//
//    @GET("flow-review/flowReviewed/getFlowReviewedByFlowId")
//    fun getFlowReviewedByFlowId( @Query("flowId") flowId :Int
//    ): Observable<String>

//    @POST("flow-review/deleteFile")
//    fun deleteFile(
//        @Query("filePath") filePath: String
//    ): Observable<String>


    @POST("flow-review/InformationManage/queryList")
    fun queryList(
        @Body params: RequestBody, @Query("pageNum") pageNum: Int
        , @Query("pageSize") pageSize: Int
    ): Observable<String>

    @GET("flow-review/InformationManage/query")
    fun query(
        @Query("flowId") flowId: Int
    ): Observable<String>

    //情报管理-新增研判结果
    @POST("flow-review/InformationManage/editSubmit")
    fun editSubmit(
        @Body params: RequestBody
    ): Observable<String>

    //情报管理-填写反馈信息
    @POST("flow-review/InformationManage/addResponse")
    fun addFeedback(
        @Body params: RequestBody
    ): Observable<String>

//    //情报管理-情报共享
//    @POST("flow-review/InformationManage/sharingInformation")
//    fun sharingInformation(
//        @Body params: RequestBody
//    ): Observable<CommonResponse<String>>



    //情报管理-更新经营状态为已完成
    @POST("flow-review/InformationManage/updateStatusToComplete")
    fun updateStatusToComplete(
        @Body params: RequestBody
    ): Observable<String>

    //流程审核 - 查询审核流程记录
    @POST("flow-review/flowReviewed/getFlowReviewedByFlowId")
    fun getFlowReviewedByFlowId(
        @Query("flowId") pageSize: Int
    ): Observable<String>


//    /**
//     * 政务微信免密登录
//     */
//    @POST("portal/synframe/sso/appLogin")
//    fun loginNoPass(@Body params: RequestBody): Observable<String>



    /**
     * 下面的是门户的接口 (现场一致的地址)
     */
//    @POST("portal/synframe/sso/login")
//    fun login(
//        @Query("loginId") loginId: String, @Query("password") password: String
//    ): Observable<String>
//
//    @POST("portal/synframe/sso/finest/auth/token")
//    fun getToken(
//        @Header(ConstantConfig.FINEST_TOKEN) token: String, @Query("applicationNo") applicationNo: Int
//    ): Observable<String>
//
//    @POST("portal/synframe/security/role/roleManage/queryRole")
//    fun queryRole(
//        @Body params: RequestBody
//    ): Observable<String>
//
//    @POST("portal/synframe/security/role/roleManage/getRolesByPid")
//    fun getRolesByPid(
//        @Query("pid") pid: Int
//    ): Observable<String>
//
//    @POST("portal/synframe/security/user-role/userRoleManage/getUsersOfRole")
//    fun getUsersOfRole(
//        @Query("roleId") roleId: Int
//    ): Observable<String>
//
//    @POST("portal/synframe/comm/getUserListByIds")
//    fun getUserListByIds(
//        @Body params: RequestBody
//    ): Observable<List<UserInfoBean>>
//
//    @GET("portal/synframe/fileService/deleteImgFile")
//    fun deleteImgFile(
//        @Query("deleteUri") uri:String
//    ): Observable<String>

//    /**
//     * 部门树相关
//     */
//    @POST("portal/synframe/getDeptsByPidFromRedis")
//    fun getDeptsByPidFromRedis(
//        @Query("pid") pid: String
//    ): Observable<List<DeptTreeItem>>

    /**
     * 政务微信免密登录
     */
    @POST("synframe/portal/synframe/sso/appLogin")
    fun loginNoPass(@Body params: RequestBody): Observable<String>

    /**
     * 下面的是门户的接口  (备用地址和正式地址用)
     */
    @GET("services/synframe/synframe/sso/login")
    fun login(
        @Query("loginId") loginId: String, @Query("password") password: String,@Query("loginTime") loginTime: String
    ): Observable<String>

    @GET("services/synframe/synframe/sso/login")
    suspend fun loginTest(
        @Query("loginId") loginId: String, @Query("password") password: String,@Query("loginTime") loginTime: String
    ): String

     //https://api.apiopen.top/videoCategoryDetails?id=14
     @GET("videoCategoryDetails")
     suspend fun testApi(
         @Query("id") loginId: Int): String

    @GET("videoCategoryDetails")
    suspend fun testFLOW(
        @Query("id") loginId: Int):String

    @GET("services/collect-tool/getAllCollectTaskByAccepterId")
    fun getTaskList(
        @Query("accepterId") accepterId: String, @Query("taskStatus") taskStatus: String
    ): Observable<String>


    @POST("synframe/portal/synframe/security/role/roleManage/queryRole")
    fun queryRole(
        @Body params: RequestBody
    ): Observable<String>

    @POST("synframe/portal/synframe/security/role/roleManage/getRolesByPid")
    fun getRolesByPid(
        @Query("pid") pid: Int
    ): Observable<String>

    @POST("synframe/portal/synframe/security/user-role/userRoleManage/getUsersOfRole")
    fun getUsersOfRole(
        @Query("roleId") roleId: Int
    ): Observable<String>



    //检查旧密码
    @GET("synframe/portal/synframe/organization/user/userManage/validateOldPassword")
    fun checkOldPassword(
        @Query("oldPassword") oldPassword: String
    ): Observable<String>
    //修改密码
    @POST("synframe/portal/synframe/organization/user/userManage/updateCurrentUserPassword")
    fun modifyPassword(
        @Query("oldPassword") oldPassword: String,@Query("newPassword") newPassword: String
    ): Observable<String>




    @POST("warning/queryWarningList")
    fun queryWarningList(
        @Body params: RequestBody
    ): Observable<String>

    @POST("warning/receiveWarning")
    fun receiveWarning(
        @Body params: RequestBody
    ): Observable<String>

    @POST("warning/feedbackWarning")
    fun feedbackWarning(
        @Body params: RequestBody
    ): Observable<String>

    /**
     * 获取预警详情，主要拿里面的卡口图片数据
     */
    @POST("warning/queryWarningDetail")
    fun warningDetail(
        @Body params: RequestBody
    ): Observable<String>

    /**
     * 一键查询
     */
    @POST("oneQuery/persionInfo")
    fun persionInfo(
        @Body params: RequestBody
    ): Observable<String>

    @POST("oneQuery/persionDetail")
    fun persionDetail(
        @Body params: RequestBody
    ): Observable<String>

//    //订票
//    @POST("specificationJson/train.json")
//    fun getTrainList(
//        @Body params: RequestBody
//    ): Observable<CommonResponse<TrainListResult>>
//    //宾馆
//    @POST("specificationJson/hotel.json")
//    fun getHotelList(
//        @Body params: RequestBody
//    ): Observable<CommonResponse<HotelListResult>>
//    //网吧
//    @POST("specificationJson/wangba.json")
//    fun getWangBaList(
//        @Body params: RequestBody
//    ): Observable<CommonResponse<WangBaListResult>>
//    //顺丰
//    @POST("specificationJson/sf.json")
//    fun getSFList(
//        @Body params: RequestBody
//    ): Observable<CommonResponse<SFListResult>>


    /********** 上传位置信息接口 **********/

    //上传警员位置信息
    @POST("determine/uploadPoliceInfo")
    fun uploadPoliceInfo(
        @Body params: RequestBody
    ): Observable<String>

}
