package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.*;
import com.econowapo.financiapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Math;
import java.util.Optional;


@Service
public class PaymentMoveServiceImpl implements PaymentMoveService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentMoveRepository paymentMoveRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditAccountMovementRepository creditAccountMovementRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Override
    public PaymentMove createPaymentMove(Long paymentId) {
        PaymentMove paymentMove = new PaymentMove();
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new ResourceNotFoundException("Payment", "Id", paymentId));
        CreditAccount creditAccount = creditAccountRepository.findById(payment.getCreditAccount().getId()).orElseThrow(() -> new ResourceNotFoundException("creditAccount", "Id", payment.getCreditAccount().getId()));
        List<CreditAccountMovement> creditAccountMovements = creditAccountMovementRepository.findByCreditAccountId(creditAccount.getId());
        List<CreditAccountMovement> creditAccountMovementsActives = new ArrayList<>();
        for (CreditAccountMovement movement: creditAccountMovements){
            if(movement.getState() == 1){
                creditAccountMovementsActives.add(movement);
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String fecha = formatter.format(date);
        paymentMove.setGenerated_date(fecha);
        paymentMove.setState(1);
        String dia = "" + fecha.charAt(0) + fecha.charAt(1);
        int day =Integer.parseInt(dia);
        //amount
        double capital = creditAccount.getBalance() - creditAccount.getActual_balance();
        System.out.println(capital);
        double dias = 30;
        double amount = 0;

        if(creditAccount.getInterest_rate() == 1) {//simple
            double interestRate = creditAccount.getInterest_rate_value() / 100;
            double t = dias/360;
            amount = capital*(1+interestRate*t);
            if(day > 2){
                amount += 10;
            }
        }
        else if (creditAccount.getInterest_rate() == 2){//nominal
            double interestRate = creditAccount.getInterest_rate_value() / 100;
            double n = dias;
            double m = dias;
            double prev = 1+interestRate/m;
            amount = capital*Math.pow(prev, n);

            if(day > 2){
                amount += 10;
            }

        }
        else {//efectiva
            double interestRate = creditAccount.getInterest_rate_value() / 100;
            double nt = 30;
            double nPeriodo = 30;
            double prev = 1+interestRate;
            double sigu = nt / nPeriodo;
            amount = capital * Math.pow(prev, sigu);

            if(day > 2){
                amount += 10;
            }
        }
        paymentMove.setAmount(amount);

        //Cambiamos el estado de los movimientos a PAGADO (2)
        for (CreditAccountMovement movement: creditAccountMovementsActives) {
            movement.setState(2);
            creditAccountMovementRepository.save(movement);
        }

        //Saldo Actual cambia a Saldo
        creditAccount.setActual_balance(creditAccount.getBalance());
        creditAccountRepository.save(creditAccount);

        paymentMove.setPayment(payment);
        paymentMoveRepository.save(paymentMove);


        return paymentMove;

    }

    @Override
    public PaymentMove updatePaymentMove(Long paymentId, Long paymentMoveId, PaymentMove paymentMove) {
        return null;
    }

    @Override
    public List<PaymentInfo> getAllPaymentMoves() {
        List<PaymentInfo> paymentsInfo = new ArrayList<>();
        List<PaymentMove> payments = paymentMoveRepository.findAll();
        for (PaymentMove pmovement: payments){
            PaymentInfo info = new PaymentInfo();
            info.setAmount(pmovement.getAmount());
            info.setGenerated_date(pmovement.getGenerated_date());
            info.setPaymentId(pmovement.getId());
            info.setState(pmovement.getState());
            Payment payment = pmovement.getPayment();
            CreditAccount creditAccount =  payment.getCreditAccount();
            Customer customer = creditAccount.getCustomer();
            info.setCustomerName(customer.getName());
            paymentsInfo.add(info);
        }

        return paymentsInfo;
    }

    @Override
    public List<PaymentInfo> getAllPaymentMovesByCustomerId(Long customerId) {
        CreditAccount creditAccount = creditAccountRepository.findByCustomerId(customerId);
        Customer customer = creditAccount.getCustomer();
        Payment payment = paymentRepository.findByCreditAccountId(creditAccount.getId());
        List<PaymentMove> paymentMoves = paymentMoveRepository.findByPaymentId(payment.getId());
        List<PaymentInfo> paymentsInfo = new ArrayList<>();

        for (PaymentMove pmovement: paymentMoves) {
            PaymentInfo info = new PaymentInfo();
            info.setCustomerName(customer.getName());
            info.setAmount(pmovement.getAmount());
            info.setGenerated_date(pmovement.getGenerated_date());
            info.setPaymentId(pmovement.getId());
            info.setState(pmovement.getState());
            paymentsInfo.add(info);
        }
        return paymentsInfo;
    }

    @Override
    public PaymentMove getPaymentMoveByIdAndPaymentId(Long Id, Long paymentId) {
        return null;
    }
}
