package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.*;
import com.econowapo.financiapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Math;
import java.util.Optional;


@Service
public class PaymentMoveServiceImpl implements PaymentMoveService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private PaymentMoveRepository paymentMoveRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditAccountMovementRepository creditAccountMovementRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Override
    public PaymentMove createPaymentMove(Long creditAccountId) {
        CreditAccount creditAccount = creditAccountRepository.findById(creditAccountId).orElseThrow(() -> new ResourceNotFoundException("creditAccount", "Id", creditAccountId));
        PaymentMove paymentMove = new PaymentMove();
        paymentMove.setState(1);
        Payment payment = paymentRepository.findByCreditAccountId(creditAccount.getId());
        List<CreditAccountMovement> creditAccountMovements = creditAccountMovementRepository.findByCreditAccountId(creditAccount.getId());
        List<CreditAccountMovement> creditAccountMovementsActives = new ArrayList<>();
        for (CreditAccountMovement movement : creditAccountMovements) {
            if (movement.getState() == 1) {
                creditAccountMovementsActives.add(movement);
            }
        }
        //varianle que guarda el pago total del mes
        double totalPago = 0;
        //hallamos el interes para cada orden generada en el mes
        for (CreditAccountMovement creditAccountMovement : creditAccountMovementsActives) {
            Order order = creditAccountMovement.getOrder();
            //hallamos el precio total de la orden
            double totalOrder = 0;
            List<Order_Detail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
            for (Order_Detail od : orderDetails) {
                totalOrder += od.getArticle().getPrice() * od.getQuantity();
            }
            //traemos la diferencia de dias
            Date lastDate = new Date();
            Date firstDate = order.getGenerated_date();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            String fd = formatter.format(firstDate);
            String ld = formatter.format(lastDate);

            LocalDate d1 = LocalDate.parse(fd, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2 = LocalDate.parse(ld, DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
            double dif = diff.toDays();
            double capital = totalOrder;
            double interestOrder = 0;

            //validamos que tipo de cuenta es y realizamos el calculo
            if (creditAccount.getInterest_rate() == 1) {//simple anual a;o ordinario 360
                System.out.println("SIMPLE MANITO GAA");
                double interestRate = creditAccount.getInterest_rate_value() / 100;
                System.out.println(interestRate+ "DIFERENCIA DE DIAS");
                double t = (dif) / 360;
                System.out.println(dif + "DIFERENCIA DE DIAS");
                interestOrder = capital * (1 + interestRate * t);
                System.out.println(interestOrder + "VALOR FUTURO");

            } else if (creditAccount.getInterest_rate() == 2) {//nominal, simepre capitalizacion diaria
                System.out.println("NOMINAL MANITO GAA");
                double interestRate = creditAccount.getInterest_rate_value() / 100;
                double n = dif;
                double m = 30;
                double prev = 1 + interestRate / m;
                interestOrder = capital * Math.pow(prev, n);
                System.out.println(interestOrder);


            } else {//efectiva
                System.out.println("EFECTIVA MANITO GAA");
                double interestRate = creditAccount.getInterest_rate_value() / 100;
                System.out.println(interestRate + " interes rate");
                double nt = dif;
                System.out.println(nt + " diferencia de dias");
                double nPeriodo = 30;
                double prev = 1 + interestRate;
                double sigu = nt / nPeriodo;
                interestOrder = capital * Math.pow(prev, sigu);
                System.out.println(interestOrder + " VALOR FUTURO ORDEN");

            }
            totalPago += interestOrder;
            System.out.println(totalPago + "TOTAL PAGO");
        }
        System.out.println(totalPago);
        paymentMove.setAmount(totalPago);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaComoCadena = sdf.format(new Date());
        paymentMove.setGenerated_date(fechaComoCadena);

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

        /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String fecha = formatter.format(date);
        paymentMove.setGenerated_date(fecha);
        paymentMove.setState(1);
        String dia = "" + fecha.charAt(0) + fecha.charAt(1);
        int day =Integer.parseInt(dia);
        //amount
        double capital = creditAccount.getBalance() - creditAccount.getActual_balance();
        System.out.println("CAPITAL");
        System.out.println(capital);
        double dias = 30;
        double amount = 0;

        if(creditAccount.getInterest_rate() == 1) {//simple
            double interestRate = creditAccount.getInterest_rate_value() / 100;
            System.out.println("INTRATE");
            System.out.println(interestRate);
            double t = dias/360;
            System.out.println("t");
            System.out.println(t);
            amount = capital*(1+interestRate*t);
            if(day > 2){
                //amount += 10;
            }
            System.out.println("AMONT");
            System.out.println(amount);
        }
        else if (creditAccount.getInterest_rate() == 2){//nominal
            double interestRate = creditAccount.getInterest_rate_value() / 100;
            double n = dias;
            double m = dias;
            double prev = 1+interestRate/m;
            amount = capital*Math.pow(prev, n);

            if(day > 2){
                //amount += 10;
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
                //amount += 10;
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
*/



    }

    @Override
    public PaymentMove updatePaymentMove(Long paymentId, Long paymentMoveId, PaymentMove paymentMove) {
        return null;
    }

    @Override
    public List<PaymentInfo> getAllPaymentMoves() {
        List<PaymentInfo> paymentsInfo = new ArrayList<>();
        List<PaymentMove> payments = paymentMoveRepository.findAll();
        for (PaymentMove pmovement : payments) {
            PaymentInfo info = new PaymentInfo();
            info.setAmount(pmovement.getAmount());
            info.setGenerated_date(pmovement.getGenerated_date());
            info.setPaymentId(pmovement.getId());
            info.setState(pmovement.getState());
            Payment payment = pmovement.getPayment();
            CreditAccount creditAccount = payment.getCreditAccount();
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

        for (PaymentMove pmovement : paymentMoves) {
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
