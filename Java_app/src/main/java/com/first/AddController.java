package com.first;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class AddController
 
{
	//addition method
	@RequestMapping(value="/processform" ,params="add",method=RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		double res;
		try {
		double a = Double.parseDouble(request.getParameter("num1"));
		double b = Double.parseDouble(request.getParameter("num2"));
		 res = a+b;
		 mv.setViewName("display.jsp");
			mv.addObject("result", res);
		}
		catch(Exception e)
		{
			mv.setViewName("display.jsp");
			mv.addObject("result", "Enter only numbers");	
		}
		return mv;
	
	}
	
	//subtraction method
	@RequestMapping(value="/processform" ,params="sub",method=RequestMethod.POST)
	public ModelAndView sub(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		double res;
		try {
		double a = Double.parseDouble(request.getParameter("num1"));
		double b = Double.parseDouble(request.getParameter("num2"));
		 res = a-b;
		 mv.setViewName("display.jsp");
			mv.addObject("result", res);
		}
		catch(Exception e)
		{
			mv.setViewName("display.jsp");
			mv.addObject("result", "Enter only numbers");	
		}
		return mv;
	
	}
	
	//multiplication method
	@RequestMapping(value="/processform" ,params="mul",method=RequestMethod.POST)
	public ModelAndView multiply(HttpServletRequest request )
	{
		ModelAndView mv = new ModelAndView();
		double res;
		try {
		double a = Double.parseDouble(request.getParameter("num1"));
		double b = Double.parseDouble(request.getParameter("num2"));
	      res = a*b;
		mv.setViewName("display.jsp");
		mv.addObject("result", res);
		}catch(Exception e)
		{
			mv.setViewName("display.jsp");
			mv.addObject("result", "Enter only numbers");
		}
		return mv;
	
	}
	
	//division method
	@RequestMapping(value="/processform" ,params="div",method=RequestMethod.POST)
	public ModelAndView divide(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		double res;
		try {
		double a = Double.parseDouble(request.getParameter("num1"));
		double b = Double.parseDouble(request.getParameter("num2"));
		if(b!=0)
		{
		 res = a/b; 
		    mv.setViewName("display.jsp");
			mv.addObject("result", res);
		}
		else
		{
			mv.setViewName("display.jsp");
			mv.addObject("result", "Invalid Input");
		}
		}
		catch(Exception e)
		{
			mv.setViewName("display.jsp");
			mv.addObject("result", "Enter only numbers");
		}
		return mv;
	}

	public double add(double a,double b)
	{
		return a+b;
	}
	public double sub(double a,double b)
	{
		return a-b;
	}
	public double mul(double a,double b)
	{
		return a*b;
	}
	public double div(double a,double b)
	{
		return a/b;
	}


}
