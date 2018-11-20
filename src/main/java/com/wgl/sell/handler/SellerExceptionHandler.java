package com.wgl.sell.handler;

import com.wgl.sell.config.ProjectUrlConfig;
import com.wgl.sell.exception.SellException;
import com.wgl.sell.exception.SellerAuthrizeException;
import com.wgl.sell.utils.ResultVoUtil;
import com.wgl.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice
public class  SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /*拦截异常*/
    @ExceptionHandler(value = SellerAuthrizeException.class)
    public ModelAndView hanlderAuthorizeException(){

       /* return new ModelAndView("redirect:".concat(projectUrlConfig.getWechatOpenAuthorize()).concat("/sell/wechat/qrAuthorize").concat("?returnUrl=")
        .concat("/sell/seller/login"));*/

       return  new ModelAndView("redirect:".concat("/seller/login"));
    }
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVo handlerSellerExption(SellException e){

        return ResultVoUtil.error(e.getCode(),e.getMessage());
    }
}
