package cn.microservices.webapp.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cn.microservices.security.Account;
import cn.microservices.security.SecurityService;
import cn.microservices.util.StringUtils;

@Controller
@SessionAttributes(types = Account.class)
public class AccountController {

    @Autowired
    private SecurityService securityService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String showAccountsList(Map<String, Object> model) {
        List<Account> accounts = securityService.findAccounts();
        model.put("accounts", accounts);
        return "accounts/accounts_list";
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public String showAccountDetails(@PathVariable String id, Map<String, Object> model) {
        Account account = securityService.getAccountById(id);
        model.put("account", account);
        return "accounts/account_details";
    }

    @RequestMapping(value = "/accounts/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
        Account account = new Account();
        model.put("account", account);
        return "accounts/create_or_update_account_form";
    }

    @RequestMapping(value = "/accounts/new", method = RequestMethod.POST)
    public String processCreationForm(String retypePassword, @Valid Account account, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "accounts/create_or_update_account_form";
        }

        if (!StringUtils.equals(account.getPassword(), retypePassword)) {
            return "accounts/create_or_update_account_form";
        }

        securityService.saveAccount(account);
        status.setComplete();
        return "redirect:/accounts/" + account.getId();
    }
}
