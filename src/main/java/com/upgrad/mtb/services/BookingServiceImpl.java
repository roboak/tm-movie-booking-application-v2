package com.upgrad.mtb.services;

import com.upgrad.mtb.entity.Movie;
import com.upgrad.mtb.entity.Theatre;
import com.upgrad.mtb.daos.BookingDAO;
import com.upgrad.mtb.entity.Booking;
import com.upgrad.mtb.dto.BookingDTO;
import com.upgrad.mtb.exceptions.*;
import com.upgrad.mtb.utils.DateDifference;
import com.upgrad.mtb.validator.BookingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service(value = "bookingService")
public class BookingServiceImpl implements BookingService  {
    @Autowired
    @Qualifier("bookingDAO")
    private BookingDAO bookingDAO  ;
    @Autowired
    CustomerService customerService;
    @Autowired
    TheatreService theatreService;
    @Autowired
    BookingValidator bookingValidator;

    public Booking acceptBookingDetails(Booking booking) throws TheatreDetailsNotFoundException, CustomerDetailsNotFoundException, BookingFailedException, APIException, ParseException {
        Theatre theatre = booking.getTheatre();
        List<Movie> moviesList = theatre.getMovies();
        if(moviesList.get(moviesList.size()-1).getStatus().getStatus().equalsIgnoreCase("Released")){
            theatreService.updateTheatreDetails(theatre.getId(), theatre );
            return bookingDAO.save(booking);
        }else{
            throw new BookingFailedException("Booking Failed");
        }

    }

    public Booking getBookingDetails(int id) throws BookingDetailsNotFoundException {
        return bookingDAO.findById(id).orElseThrow(
                ()->  new BookingDetailsNotFoundException("Details not found for id : " + id));
    }


    public boolean deleteBooking(int id) throws BookingDetailsNotFoundException {
        Booking booking = getBookingDetails(id);
        bookingDAO.delete(booking);
        return true;
    }

    public List<Booking> getAllBookingDetails() {
        return bookingDAO.findAll();
    }
}
